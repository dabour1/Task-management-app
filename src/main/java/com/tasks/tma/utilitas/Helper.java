package com.tasks.tma.utilitas;

import com.tasks.tma.exception.ResourceNotFoundException;
import com.tasks.tma.models.Task;
import com.tasks.tma.models.User;
import com.tasks.tma.models.Workspace;
import com.tasks.tma.repository.TaskRepository;
import com.tasks.tma.repository.UserRepository;
import com.tasks.tma.repository.WorkspaceRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Data
@AllArgsConstructor
@Component
public class Helper {
    private final WorkspaceRepository workspaceRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public Workspace findWorkspaceById(UUID workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new ResourceNotFoundException("Workspace not found with id: " + workspaceId));
    }

    public User findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }
    public Task findTaskById(UUID userId){
        return taskRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + userId));
    }
}
