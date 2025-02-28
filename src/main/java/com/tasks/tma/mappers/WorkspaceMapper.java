package com.tasks.tma.mappers;

import com.tasks.tma.dtos.WorkspaceRequestDTO;
import com.tasks.tma.dtos.WorkspaceResponseDTO;
import com.tasks.tma.models.Workspace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WorkspaceMapper {

    WorkspaceMapper INSTANCE = Mappers.getMapper(WorkspaceMapper.class);

    @Mapping(source = "user.id", target = "userId")
    WorkspaceResponseDTO workspaceToWorkspaceResponseDTO(Workspace workspace);

    @Mapping(source = "userId", target = "user.id")
    Workspace workspaceRequestDTOoWorkspace(WorkspaceRequestDTO workspaceRequestDTO);
 }
