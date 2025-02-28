package com.tasks.tma.dtos;

import lombok.Data;
import java.time.Instant;
import java.util.UUID;
@Data
public class WorkspaceResponseDTO {

    private UUID id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID userId;


}
