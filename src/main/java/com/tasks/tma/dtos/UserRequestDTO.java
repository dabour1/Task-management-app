package com.tasks.tma.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {
    @NotBlank(message = "phoneNumber is required")
    @NotNull(message ="phoneNumber cannot be null")
    @Size(min = 8, max = 12, message = "phoneNumber must be between 8 and 12 characters")
    private String phoneNumber;

    @NotBlank(message = "password is required")
    @NotNull(message ="password cannot be null")
    @Size(min = 8, max = 50, message = "password must be between 2 and 50 characters")
    private String password;

    @NotBlank(message = "email is required")
    @NotNull(message ="email cannot be null")
    @Email(message ="invalid format" )
    private String email;
}
