package com.tasks.tma.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthenticationRequest {
    @NotNull(message ="email cannot be null")
    @Email(message ="invalid format" )
    private String email;
    private String password;

}
