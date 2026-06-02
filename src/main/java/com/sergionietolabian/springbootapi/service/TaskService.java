package com.sergionietolabian.springbootapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sergionietolabian.springbootapi.dto.TaskPatchRequestDTO;
import com.sergionietolabian.springbootapi.dto.TaskRequestDTO;
import com.sergionietolabian.springbootapi.dto.TaskResponseDTO;
import com.sergionietolabian.springbootapi.dto.TaskUpdateRequestDTO;
import com.sergionietolabian.springbootapi.entity.Task;
import com.sergionietolabian.springbootapi.enums.TaskStatus;
import com.sergionietolabian.springbootapi.mapper.TaskMapper;
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
    
    public TaskResponseDTO getTaskById(Long id) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task not found"));

        TaskResponseDTO response = new TaskResponseDTO();

        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());

        return response;
    }
    
    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }
    
    public List<Task> getTasksByStatusAndTitle(TaskStatus status, String title) {
        return taskRepository.findByStatusAndTitleContaining(status, title);
    }
    
    public TaskResponseDTO createTask(TaskRequestDTO dto) {

        Task task = TaskMapper.toEntity(dto);

        Task savedTask = taskRepository.save(task);

        return TaskMapper.toResponse(savedTask);
    }
    
    public TaskResponseDTO updateTask(Long id, TaskUpdateRequestDTO dto) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());

        Task updated = taskRepository.save(task);

        TaskResponseDTO response = new TaskResponseDTO();
        response.setId(updated.getId());
        response.setTitle(updated.getTitle());
        response.setDescription(updated.getDescription());
        response.setStatus(updated.getStatus());

        return response;
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

        TaskResponseDTO response = new TaskResponseDTO();
        response.setId(updated.getId());
        response.setTitle(updated.getTitle());
        response.setDescription(updated.getDescription());
        response.setStatus(updated.getStatus());

        return response;
    }
}