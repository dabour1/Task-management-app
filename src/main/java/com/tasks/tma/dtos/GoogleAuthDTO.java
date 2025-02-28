package com.tasks.tma.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GoogleAuthDTO {
    @NotNull(message ="idToken cannot be null")
    @NotBlank(message ="idToken cannot be blank")
    private String idToken;
}
