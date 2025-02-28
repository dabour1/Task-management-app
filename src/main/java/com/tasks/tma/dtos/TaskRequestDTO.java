package com.tasks.tma.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.UUID;
@Data
public class TaskRequestDTO {
    @NotNull(message = "Workspace ID cannot be null")
    private UUID workspaceId;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;

    private String description;

    @Pattern(regexp = "^(PENDING|DONE)$", message = "Status must be either 'PENDING' or 'DONE'")
    private String status;

}
