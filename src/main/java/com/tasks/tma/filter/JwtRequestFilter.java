package com.tasks.tma.filter;

 import com.tasks.tma.service.JwtService;
 import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
            throws ServletException, IOException {
        try {
            if (isExcludedRequest(request)) {
                chain.doFilter(request, response);
                return;
            }

            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                chain.doFilter(request, response);
                return;
            }

            String jwt = extractJwt(authorizationHeader);
            String email = jwtService.extractEmail(jwt);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                authenticateUser(email, jwt, request);
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            handleException(response);
        }
    }

    private boolean isExcludedRequest(HttpServletRequest request) {
        return request.getServletPath().contains("/api/auth");
    }

    private String extractJwt(String authorizationHeader) {
        return authorizationHeader.substring(7);
    }

    private void authenticateUser(String email, String jwt, HttpServletRequest request) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

        if (jwtService.validateToken(jwt, userDetails.getUsername())) {
            UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationToken(userDetails, request);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private UsernamePasswordAuthenticationToken createAuthenticationToken(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    private void handleException(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
    }

}
