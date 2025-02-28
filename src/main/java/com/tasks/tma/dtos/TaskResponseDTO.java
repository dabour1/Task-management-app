package com.tasks.tma.dtos;

import com.tasks.tma.enums.TaskStatus;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Data
public class TaskResponseDTO {

    private UUID id;
    private UUID workspaceId;
    private String title;
    private String description;
    private TaskStatus status ;
    private Instant createdAt ;
    private Instant updatedAt ;
}
