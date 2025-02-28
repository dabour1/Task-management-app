package com.tasks.tma.service;


import com.tasks.tma.dtos.UserRequestDTO;
import com.tasks.tma.dtos.AuthenticationRequest;
import com.tasks.tma.dtos.AuthenticationResponse;
import com.tasks.tma.exception.ResourceNotFoundException;
import com.tasks.tma.exception.UniqueConstraintViolationException;
import com.tasks.tma.mappers.UserMapper;
import com.tasks.tma.models.User;
import com.tasks.tma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserRequestDTO request) {
        try {
            User user = userMapper.userDtoTouser(request);
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            user = userRepository.save(user);
            return createAuthenticationResponse(user);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintViolationException("Phone number or email already exists" );
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Incorrect email or password", e);
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return createAuthenticationResponse(user);
    }

    private AuthenticationResponse createAuthenticationResponse(User user) {
        String jwtToken = jwtService.generateToken(user.getUsername());
        return new AuthenticationResponse(jwtToken, user.getId());
    }
}

