package com.plociennik.service.auth;

import com.plociennik.model.UserEntity;
import com.plociennik.model.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final PasswordEncoder passwordEncoder;

    private static final String LOGIN_SUCCESS_MESSAGE = "Logged in";
    private static final String LOGIN_FAILURE_MESSAGE = "Invalid credentials";

    public ResponseEntity<AuthResponse> auth(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> {
                    logFailure(loginRequest.username(), "180501132025");
                    return new UsernameNotFoundException(LOGIN_FAILURE_MESSAGE);
                });

        boolean doesPasswordMatch = passwordEncoder.matches(loginRequest.password(), user.getPassword());
        if (doesPasswordMatch) {
            String token = jwtTokenUtil.generateToken(user);
            logSuccess(loginRequest.username());
            AuthResponse authResponse = new AuthResponse(token, LOGIN_SUCCESS_MESSAGE);
            return ResponseEntity.ok(authResponse);
        } else {
            logFailure(loginRequest.username(), "192401132025");
            throw new UsernameNotFoundException(LOGIN_FAILURE_MESSAGE);
        }
    }

    private void logSuccess(String username) {
        log.info("User successfully logged in, username: {}, (EID: {})", username, "081115122024");
    }

    private void logFailure(String username, String eid) {
        log.info("User failed to log in, username: {}, (EID: {})", username, eid);
    }
}

