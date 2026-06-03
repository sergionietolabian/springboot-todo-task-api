package com.sergionietolabian.springbootapi.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sergionietolabian.springbootapi.dto.TaskPatchRequestDTO;
import com.sergionietolabian.springbootapi.dto.TaskRequestDTO;
import com.sergionietolabian.springbootapi.dto.TaskResponseDTO;
import com.sergionietolabian.springbootapi.dto.TaskUpdateRequestDTO;
import com.sergionietolabian.springbootapi.entity.Task;
import com.sergionietolabian.springbootapi.entity.User;
import com.sergionietolabian.springbootapi.enums.TaskStatus;
import com.sergionietolabian.springbootapi.mapper.TaskMapper;
import com.sergionietolabian.springbootapi.repository.TaskRepository;
import com.sergionietolabian.springbootapi.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, JwtService jwtService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public List<TaskResponseDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public TaskResponseDTO getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        return TaskMapper.toResponse(task);
    }
    
    public List<TaskResponseDTO> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(TaskMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public List<TaskResponseDTO> getTasksByStatusAndTitle(TaskStatus status, String title) {
        return taskRepository.findByStatusAndTitleContaining(status, title)
                .stream()
                .map(TaskMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public TaskResponseDTO createTask(TaskRequestDTO dto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setUser(user);

        Task saved = taskRepository.save(task);

        return TaskMapper.toResponse(saved);
    }
    
    public TaskResponseDTO updateTask(Long id, TaskUpdateRequestDTO dto) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());

        Task updated = taskRepository.save(task);

        return TaskMapper.toResponse(updated);
    }
    
    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));

        taskRepository.delete(task);
    }
    
    public TaskResponseDTO patchTask(Long id, TaskPatchRequestDTO dto) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));

        if (dto.getTitle() != null) {
            task.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            task.setDescription(dto.getDescription());
        }

        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }

        Task updated = taskRepository.save(task);

        return TaskMapper.toResponse(updated);
    }
}