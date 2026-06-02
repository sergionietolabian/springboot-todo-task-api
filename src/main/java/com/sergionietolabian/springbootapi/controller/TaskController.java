package com.sergionietolabian.springbootapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergionietolabian.springbootapi.dto.TaskRequestDTO;
import com.sergionietolabian.springbootapi.dto.TaskResponseDTO;
import com.sergionietolabian.springbootapi.dto.TaskUpdateRequestDTO;
import com.sergionietolabian.springbootapi.entity.Task;
import com.sergionietolabian.springbootapi.enums.TaskStatus;
import com.sergionietolabian.springbootapi.service.TaskService;

import jakarta.validation.Valid;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }
    
    @GetMapping("/tasks/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
    
    @GetMapping("/tasks/status")
    public List<Task> getTasksByStatus(@RequestParam TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }
    
    @GetMapping("/tasks/search")
    public List<Task> getTasksByStatusAndTitle(
            @RequestParam TaskStatus status,
            @RequestParam String title) {
        return taskService.getTasksByStatusAndTitle(status, title);
    }
    
    @PostMapping("/tasks")
    public TaskResponseDTO createTask(
            @Valid @RequestBody TaskRequestDTO dto) {

        return taskService.createTask(dto);
    }
    
    @PutMapping("/tasks/{id}")
    public TaskResponseDTO updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequestDTO dto) {

        return taskService.updateTask(id, dto);
    }
    
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}