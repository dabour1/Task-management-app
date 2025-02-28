package com.tasks.tma.service;


import com.tasks.tma.dtos.UserRequestDTO;
import com.tasks.tma.dtos.AuthenticationRequest;
import com.tasks.tma.dtos.AuthenticationResponse;
import com.tasks.tma.exception.UniqueConstraintViolationException;
import com.tasks.tma.mappers.UserMapper;
import com.tasks.tma.models.User;
import com.tasks.tma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final  UserRepository userRepository;

    private final  PasswordEncoder passwordEncoder;

    private final  JwtService jwtService;

    private final  UserMapper userMapper;

    private final  AuthenticationManager authenticationManager;

    private final  UserDetailsService userDetailsService;
    
    public AuthenticationResponse register(UserRequestDTO request){
        try {
            User userEntity = userMapper.userDtoTouser(request);

            userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
            User user = userRepository.save(userEntity);
            final String jwtToken = jwtService.generateToken(user.getUsername());

            return new AuthenticationResponse(jwtToken);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueConstraintViolationException("Phone number or email already exists");
        }

    }

    public AuthenticationResponse authenticat(AuthenticationRequest request) throws Exception  {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),
                     request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Incorrect Email or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwtToken = jwtService.generateToken(userDetails.getUsername());

        return new AuthenticationResponse(jwtToken);
    }

}
