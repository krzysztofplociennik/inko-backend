package com.plociennik.web.api;

import com.plociennik.common.errorhandling.exceptions.LoginCredentialsInvalidException;
import lombok.AllArgsConstructor;
import com.plociennik.service.auth.AuthResponse;
import com.plociennik.service.auth.AuthService;
import com.plociennik.service.auth.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return authService.auth(loginRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String authHeader) {
        return authService.refreshToken(authHeader);
    }
}
