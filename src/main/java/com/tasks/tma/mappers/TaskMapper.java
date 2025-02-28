package com.tasks.tma.mappers;

import com.tasks.tma.dtos.TaskResponseDTO;
 import com.tasks.tma.models.Task;
 import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
 import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "workspace.id", target = "workspaceId")
     TaskResponseDTO taskToTaskResponseDTO(Task task);


}
