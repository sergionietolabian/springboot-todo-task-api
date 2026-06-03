package com.sergionietolabian.springbootapi.mapper;

import com.sergionietolabian.springbootapi.dto.TaskRequestDTO;
import com.sergionietolabian.springbootapi.dto.TaskResponseDTO;
import com.sergionietolabian.springbootapi.entity.Task;

public class TaskMapper {

    public static TaskResponseDTO toResponse(Task task) {
	    TaskResponseDTO dto = new TaskResponseDTO();

	    dto.setId(task.getId());
	    dto.setTitle(task.getTitle());
	    dto.setDescription(task.getDescription());
	    dto.setStatus(task.getStatus());

	    dto.setUsername(
	        task.getUser() != null ? task.getUser().getUsername() : null
	    );

	    return dto;
    }

    public static Task toEntity(TaskRequestDTO dto) {

        Task task = new Task();

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());

        return task;
    }
}