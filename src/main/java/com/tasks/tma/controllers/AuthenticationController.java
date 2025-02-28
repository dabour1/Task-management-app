package com.tasks.tma.controllers;


import com.tasks.tma.dtos.GoogleAuthDTO;
import com.tasks.tma.dtos.UserRequestDTO;
import com.tasks.tma.dtos.AuthenticationRequest;
import com.tasks.tma.dtos.AuthenticationResponse;
import com.tasks.tma.service.AuthenticationService;
import com.tasks.tma.service.GoogleAuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final  AuthenticationService authenticationService;
    private final GoogleAuthService googleAuthService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid UserRequestDTO user) throws Exception {
        return ResponseEntity.ok(authenticationService.register(user)) ;
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) throws Exception {
        return ResponseEntity.ok(authenticationService.authenticate(request)) ;
    }
    @PostMapping("/google")
    public ResponseEntity<?> login(@RequestBody @Valid GoogleAuthDTO googleAuthDTO) throws Exception {
        return ResponseEntity.ok( googleAuthService.verifyGoogleToken(googleAuthDTO.getIdToken()));
    }
}
