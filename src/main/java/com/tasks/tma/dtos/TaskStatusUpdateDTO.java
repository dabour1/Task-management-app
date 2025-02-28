package com.tasks.tma.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TaskStatusUpdateDTO {
    @NotNull(message = "Status is required")
    @Pattern(regexp = "^(PENDING|DONE)$", message = "Status must be either 'PENDING' or 'DONE'")
    private String status;

}
