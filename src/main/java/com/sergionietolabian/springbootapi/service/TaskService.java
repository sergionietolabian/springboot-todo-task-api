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
    
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> getAllTasks() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return taskRepository.findByUserUsername(username)
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

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(TaskStatus.PENDING);
        task.setUser(user);

        return TaskMapper.toResponse(taskRepository.save(task));
    }
    
    public TaskResponseDTO updateTask(Long id, TaskUpdateRequestDTO dto) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Task task = taskRepository.findById(id)
                .orElseThrow();

        if (task != null && task.getUser() != null && !task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No tienes permiso");
        }else if(task == null){
            throw new RuntimeException("No existe la tarea");
        }else if(task.getUser() == null){
            throw new RuntimeException("No existe el usuario");
        }

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        return TaskMapper.toResponse(taskRepository.save(task));
    }
    
    public void deleteTask(Long id) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Task task = taskRepository.findById(id)
                .orElseThrow();

        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("No tienes permiso");
        }

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
    
    public List<TaskResponseDTO> getMyTasks() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return taskRepository.findByUserUsername(username)
                .stream()
                .map(TaskMapper::toResponse)
                .collect(Collectors.toList());
    }
}