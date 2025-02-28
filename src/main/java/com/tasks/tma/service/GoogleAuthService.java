package com.tasks.tma.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.tasks.tma.dtos.AuthenticationResponse;
import com.tasks.tma.models.User;
import com.tasks.tma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.client.json.gson.GsonFactory;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${google.client-id}")
    private String googleClientId;


    public AuthenticationResponse verifyGoogleToken(String token) {
        try {
            GoogleIdToken idToken = verifyTokenWithGoogle(token);
            String email = extractEmailFromToken(idToken);

            User user = findOrCreateUserByEmail(email);
            String jwtToken = generateJwtToken(user);

            return new AuthenticationResponse(jwtToken,user.getId());
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed", e);
        }
    }

    private GoogleIdToken verifyTokenWithGoogle(String token) throws Exception {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken idToken = verifier.verify(token);
        if (idToken == null) {
            throw new RuntimeException("Invalid Google ID Token");
        }
        return idToken;
    }

    private String extractEmailFromToken(GoogleIdToken idToken) {
        GoogleIdToken.Payload payload = idToken.getPayload();
        return payload.getEmail();
    }

    private User findOrCreateUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setPhoneNumber("");
                    newUser.setPassword("");
                    return userRepository.save(newUser);
                });
    }

    private String generateJwtToken(User user) {
        return jwtService.generateToken(user.getEmail());
    }
}