package com.sergionietolabian.springbootapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sergionietolabian.springbootapi.entity.Task;
import com.sergionietolabian.springbootapi.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }
}