package com.tasks.tma.mappers;

import com.tasks.tma.dtos.TaskResponseDTO;
import com.tasks.tma.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskPaginationMapper {
    TaskPaginationMapper INSTANCE = Mappers.getMapper(TaskPaginationMapper.class);

    @Mapping(source = "task.id", target = "id")
    @Mapping(source = "task.title", target = "title")
    @Mapping(source = "task.description", target = "description")
    @Mapping(source = "task.status", target = "status")
    TaskResponseDTO mapToTaskResponseDTO(Task task);
}
