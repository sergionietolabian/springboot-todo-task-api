package com.sergionietolabian.springbootapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.sergionietolabian.springbootapi.dto.TaskResponseDTO;
import com.sergionietolabian.springbootapi.service.TaskService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admin", description = "Operaciones administrativas")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final TaskService taskService;

    public AdminController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}