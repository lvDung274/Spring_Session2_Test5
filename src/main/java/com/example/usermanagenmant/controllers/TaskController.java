package com.example.usermanagenmant.controllers;

import com.example.usermanagenmant.models.Task;
import com.example.usermanagenmant.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/app/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> findAllTasks(
            @RequestParam(value = "search", required = false) String search
    ) {
        List<Task> tasks = taskService.findTasksWithSearch(search);
        return ResponseEntity.ok(tasks);
    }
    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody Task newTask) {
        Task createdTask = taskService.createTask(newTask);
        if (createdTask == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: User with ID " + newTask.getAssignedTo() + " does not exist!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable int id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);

        if (updatedTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Task with ID " + id + " does not exist !");
        }
        return ResponseEntity.ok(updatedTask);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable int id) {
        Task deletedTask = taskService.deleteTask(id);

        if (deletedTask == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Task with ID " + id + " does not exist!");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}