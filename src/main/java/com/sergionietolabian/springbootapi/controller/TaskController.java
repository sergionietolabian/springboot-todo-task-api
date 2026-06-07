package com.sergionietolabian.springbootapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sergionietolabian.springbootapi.dto.*;
import com.sergionietolabian.springbootapi.enums.TaskStatus;
import com.sergionietolabian.springbootapi.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Tasks", description = "Operaciones relacionadas con tareas")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ======================================================
    // USER - SUS TAREAS
    // ======================================================

    @Operation(summary = "Obtener mis tareas")
    @GetMapping("/me")
    public List<TaskResponseDTO> getMyTasks() {
        return taskService.getMyTasks();
    }

    @Operation(summary = "Crear tarea para el usuario logueado")
    @PostMapping
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO dto) {
        return taskService.createTask(dto);
    }

    @Operation(summary = "Actualizar tarea del usuario")
    @PutMapping("/{id}")
    public TaskResponseDTO updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequestDTO dto) {

        return taskService.updateTask(id, dto);
    }

    @Operation(summary = "Eliminar tarea del usuario")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Actualizar parcialmente una tarea")
    @PatchMapping("/{id}")
    public TaskResponseDTO patchTask(
            @PathVariable Long id,
            @RequestBody TaskPatchRequestDTO dto) {

        return taskService.patchTask(id, dto);
    }

    // ======================================================
    // USER - FILTROS
    // ======================================================

    @Operation(summary = "Obtener tareas por estado")
    @GetMapping("/status")
    public List<TaskResponseDTO> getTasksByStatus(@RequestParam TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }

    @Operation(summary = "Buscar tareas por estado y título")
    @GetMapping("/search")
    public List<TaskResponseDTO> searchTasks(
            @RequestParam TaskStatus status,
            @RequestParam String title) {

        return taskService.getTasksByStatusAndTitle(status, title);
    }

    @Operation(summary = "Obtener tarea por ID")
    @GetMapping("/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
}