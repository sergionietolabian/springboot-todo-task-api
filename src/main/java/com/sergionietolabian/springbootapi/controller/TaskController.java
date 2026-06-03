package com.sergionietolabian.springbootapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sergionietolabian.springbootapi.dto.TaskPatchRequestDTO;
import com.sergionietolabian.springbootapi.dto.TaskRequestDTO;
import com.sergionietolabian.springbootapi.dto.TaskResponseDTO;
import com.sergionietolabian.springbootapi.dto.TaskUpdateRequestDTO;
import com.sergionietolabian.springbootapi.enums.TaskStatus;
import com.sergionietolabian.springbootapi.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Tasks", description = "Operaciones relacionadas con tareas")
@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Obtener todas las tareas",
            description = "Devuelve la lista completa de tareas"
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping("/tasks")
    public List<TaskResponseDTO> getTasks() {
        return taskService.getAllTasks();
    }
    
    @Operation(
            summary = "Obtener tarea por ID",
            description = "Devuelve una tarea específica según su identificador"
    )
    @ApiResponse(responseCode = "200", description = "Tarea encontrada")
    @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    @GetMapping("/tasks/{id}")
    public TaskResponseDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
    
    @Operation(
            summary = "Obtener tareas por estado",
            description = "Filtra tareas según su estado (PENDING, IN_PROGRESS, DONE)"
    )
    @ApiResponse(responseCode = "200", description = "Lista filtrada correctamente")
    @GetMapping("/tasks/status")
    public List<TaskResponseDTO> getTasksByStatus(@RequestParam TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }
    
    @Operation(
            summary = "Buscar tareas",
            description = "Filtra tareas por estado y texto en el título"
    )
    @ApiResponse(responseCode = "200", description = "Resultados obtenidos correctamente")
    @GetMapping("/tasks/search")
    public List<TaskResponseDTO> getTasksByStatusAndTitle(
            @RequestParam TaskStatus status,
            @RequestParam String title) {
        return taskService.getTasksByStatusAndTitle(status, title);
    }
    
    @Operation(
            summary = "Crear una nueva tarea",
            description = "Crea una tarea en la base de datos con título, descripción y estado"
    )
    @ApiResponse(responseCode = "201", description = "Tarea creada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    @PostMapping("/tasks")
    public TaskResponseDTO createTask(@RequestBody TaskRequestDTO dto) {
        return taskService.createTask(dto);
    }
    
    @Operation(
            summary = "Actualizar tarea completa",
            description = "Reemplaza todos los campos de una tarea existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarea actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/tasks/{id}")
    public TaskResponseDTO updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequestDTO dto) {

        return taskService.updateTask(id, dto);
    }
    
    @Operation(
            summary = "Eliminar tarea",
            description = "Elimina una tarea por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarea eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Tarea no encontrada")
    })
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
    
    @Operation(
            summary = "Actualizar parcialmente una tarea",
            description = "Actualiza solo los campos enviados en la petición"
    )
    @PatchMapping("/tasks/{id}")
    public TaskResponseDTO patchTask(
            @PathVariable Long id,
            @RequestBody TaskPatchRequestDTO dto) {

        return taskService.patchTask(id, dto);
    }
}