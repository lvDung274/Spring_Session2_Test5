package com.example.usermanagenmant.repositories;

import com.example.usermanagenmant.models.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TaskRepository {
       List<Task> tasks =  new ArrayList<Task>(
               Arrays.asList(
                       new Task(1L, "Thiết kế Database", "Xây dựng sơ đồ ERD", "high", 1L),
                       new Task(2L, "Viết API Đăng nhập", "Xác thực JWT", "high", 2L),
                       new Task(3L, "Cấu hình Security", "Spring Security config", "medium", 1L),
                       new Task(4L, "Kiểm thử Unit Test", "JUnit 5 cho Service", "low", 3L),
                       new Task(5L, "Thiết kế giao diện Login", "HTML/CSS & Tailwind", "medium", 2L),
                       new Task(6L, "Tích hợp thanh toán", "VNPay / Momo gateway", "high", 1L),
                       new Task(7L, "Fix lỗi CORS", "Cấu hình WebMvcConfigurer", "low", 3L),
                       new Task(8L, "Viết tài liệu Swagger", "OpenAPI documentation", "medium", 2L),
                       new Task(9L, "Deploy lên Server", "AWS EC2 setup", "high", 1L),
                       new Task(10L, "Tối ưu hóa câu lệnh SQL", "Index các bảng chính", "high", 3L)
               )
       );

    private Long idCounter = 11L;

       public List<Task> findAllTasks() {
           return tasks;
       }
    public Task save(Task task) {
        task.setId(idCounter++);
        tasks.add(task);
        return task;
    }
    public Task findById(int id) {
        return tasks.stream()
                .filter(task -> task.getId() != null && task.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Task save(int id, Task updatedTask) {
        Task existingTask = findById(id);
        if (existingTask == null) {
            return null;
        }
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setAssignedTo(updatedTask.getAssignedTo());

        return existingTask;
    }
    public Task deleteById(int id) {
        Task existingTask = findById(id);
        if (existingTask == null) {
            return null;
        }
        tasks.remove(existingTask);
        return existingTask;
    }
}
