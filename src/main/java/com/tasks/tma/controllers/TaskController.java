package com.tasks.tma.controllers;

import com.tasks.tma.dtos.TaskRequestDTO;
import com.tasks.tma.dtos.TaskResponseDTO;
import com.tasks.tma.dtos.TaskStatusUpdateDTO;
import com.tasks.tma.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    @GetMapping
    public ResponseEntity<?> getAllTasks (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(taskService.getAllTasks(page,size));
    }
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.createTask(taskRequestDTO));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable UUID id, @RequestBody @Valid TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskRequestDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{taskId}/status")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(
            @PathVariable UUID taskId, @RequestBody @Valid TaskStatusUpdateDTO taskStatusUpdateDTO) {
        return ResponseEntity.ok(taskService.updateTaskStatus(taskId, taskStatusUpdateDTO));

    }
    @GetMapping("/by-workspace/{workspaceId}")
    public ResponseEntity<?> getTasksByWorkspace(@PathVariable UUID workspaceId) {
        return ResponseEntity.ok(taskService.getTasksByWorkspace(workspaceId));
    }

}
