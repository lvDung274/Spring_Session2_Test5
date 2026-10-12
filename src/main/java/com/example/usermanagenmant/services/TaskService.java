package com.example.usermanagenmant.services;

import com.example.usermanagenmant.models.Task;
import com.example.usermanagenmant.models.User;
import com.example.usermanagenmant.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAllTasks();
    }
    public List<Task> findTasksWithSearch(String search) {
        List<Task> tasks = findAllTasks();

        if (search != null && !search.trim().isEmpty()) {
            String keyword = search.toLowerCase().trim();
            return tasks.stream()
                    .filter(task -> task.getTitle() != null &&
                            task.getTitle().toLowerCase().contains(keyword))
                    .collect(Collectors.toList());
        }

        return tasks;
    }
    public Task createTask(Task newTask) {
        User existingUser = userService.findUserById(newTask.getAssignedTo());

        if (existingUser == null) {
            return null;
        }
        return taskRepository.save(newTask);
    }

    public Task updateTask(int id, Task updatedTask) {
        User existingUser = userService.findUserById(updatedTask.getAssignedTo());
        if (existingUser == null) {
            return null;
        }

        return taskRepository.save(id, updatedTask);
    }
    public Task deleteTask(int id) {
        return taskRepository.deleteById(id);
    }
}