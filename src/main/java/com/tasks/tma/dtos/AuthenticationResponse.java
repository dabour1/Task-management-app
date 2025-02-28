package com.tasks.tma.dtos;

import lombok.AllArgsConstructor;
import lombok.Data ;

import java.util.UUID;
@AllArgsConstructor
@Data
public class AuthenticationResponse {
    private String token;
    private UUID userId;
}
