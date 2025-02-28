package com.tasks.tma.dtos;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class WorkspaceRequestDTO {

    @NotNull(message = "user_id cannot be null")
    private UUID userId;

    @NotBlank(message = "workspace name is required")
    @Size(min = 3, max = 50, message = "workspace name must be between 3 and 50 characters")
    private String name;

    // Getters and Setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
