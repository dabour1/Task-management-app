package com.tasks.tma.service;

import com.tasks.tma.dtos.WorkspaceRequestDTO;
import com.tasks.tma.dtos.WorkspaceResponseDTO;
import com.tasks.tma.exception.ResourceNotFoundException;
import com.tasks.tma.exception.UniqueConstraintViolationException;
import com.tasks.tma.mappers.WorkspaceMapper;
import com.tasks.tma.models.User;
import com.tasks.tma.models.Workspace;
import com.tasks.tma.repository.WorkspaceRepository;
import com.tasks.tma.utilitas.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMapper workspaceMapper;
    private final Helper helper;

    public List<WorkspaceResponseDTO> getAllWorkspaces() {
        return workspaceRepository.findAll()
                .stream()
                .map(workspaceMapper::workspaceToWorkspaceResponseDTO)
                .collect(Collectors.toList());
    }

    public WorkspaceResponseDTO getWorkspaceById(UUID workspaceId) {
        Workspace workspace = helper.findWorkspaceById(workspaceId);
        return workspaceMapper.workspaceToWorkspaceResponseDTO(workspace);
    }

    public WorkspaceResponseDTO createWorkspace(WorkspaceRequestDTO workspaceRequest) {
        validateWorkspaceName(workspaceRequest.getName(), null);
        User user = helper.findUserById(workspaceRequest.getUserId());

        Workspace workspace = createWorkspaceObject(workspaceRequest.getName(),user);

        workspaceRepository.save(workspace);
        return workspaceMapper.workspaceToWorkspaceResponseDTO(workspace);
    }


    public WorkspaceResponseDTO updateWorkspace(UUID workspaceId, WorkspaceRequestDTO workspaceRequest) {
        Workspace existingWorkspace = helper.findWorkspaceById(workspaceId);
        validateWorkspaceName(workspaceRequest.getName(), workspaceId);

        User user = helper.findUserById(workspaceRequest.getUserId());
        updateWorkspaceDetails(existingWorkspace, workspaceRequest, user);

        workspaceRepository.save(existingWorkspace);
        return workspaceMapper.workspaceToWorkspaceResponseDTO(existingWorkspace);
    }

    public void deleteWorkspace(UUID workspaceId) {
        if (!workspaceRepository.existsById(workspaceId)) {
            throw new ResourceNotFoundException("Workspace not found with id: " + workspaceId);
        }
        workspaceRepository.deleteById(workspaceId);
    }

    private void validateWorkspaceName(String name, UUID excludeId) {
        if (excludeId == null) {
            if (workspaceRepository.existsByName(name)) {
                throw new UniqueConstraintViolationException("Workspace with name: " + name + " already exists");
            }
        } else {
            if (workspaceRepository.existsByNameAndIdNot(name, excludeId)) {
                throw new UniqueConstraintViolationException("Workspace with name: " + name + " already exists");
            }
        }
    }
    private Workspace createWorkspaceObject(String name, User user){
        return Workspace.builder()
                .name(name)
                .user(user)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }
    private void updateWorkspaceDetails(Workspace Workspace, WorkspaceRequestDTO workspaceRequestDTO , User user) {
        Workspace.setName(workspaceRequestDTO.getName());
        Workspace.setUser(user);
    }
}
