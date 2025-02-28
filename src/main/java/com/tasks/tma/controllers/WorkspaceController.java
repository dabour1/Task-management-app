package com.tasks.tma.controllers;

import com.tasks.tma.dtos.WorkspaceRequestDTO;
import com.tasks.tma.service.TaskService;
import com.tasks.tma.service.WorkspaceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/workspaces")
@AllArgsConstructor
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final TaskService taskService;
    @GetMapping
    public ResponseEntity<?> getAllWorkspaces() {
        return ResponseEntity.ok(workspaceService.getAllWorkspaces());
    }
    @PostMapping
    public ResponseEntity<?> createWorkspace( @RequestBody @Valid WorkspaceRequestDTO workspaceRequest) {
        return ResponseEntity.ok(workspaceService.createWorkspace(workspaceRequest));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkspaceById(@PathVariable UUID id) {
        return ResponseEntity.ok(workspaceService.getWorkspaceById(id));
     }
     @PutMapping("/{id}")
     public ResponseEntity<?> updateWorkspace(@PathVariable UUID id, @RequestBody @Valid WorkspaceRequestDTO workspaceRequest) {
            return ResponseEntity.ok(workspaceService.updateWorkspace(id, workspaceRequest));
     }
     @DeleteMapping("/{id}")
     public ResponseEntity<?> deleteWorkspace(@PathVariable UUID id) {
         workspaceService.deleteWorkspace(id);
         return ResponseEntity.noContent().build();
     }
    @GetMapping("/{workspaceId}/tasks")
    public ResponseEntity<?> getTasksByWorkspace(@PathVariable UUID workspaceId) {
        return ResponseEntity.ok(taskService.getTasksByWorkspace(workspaceId));
    }

}
