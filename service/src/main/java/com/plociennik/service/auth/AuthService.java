package com.plociennik.service.auth;

import com.plociennik.model.UserEntity;
import com.plociennik.model.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<String> auth(LoginRequest loginRequest) {
        try {

            UserEntity user = userRepository.findByUsername(loginRequest.username())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String token = jwtTokenUtil.generateToken(user);
            ResponseEntity.ok(new AuthResponse(token));

            log.info("Logged in baby");
            return ResponseEntity.ok("logged in");
        } catch (BadCredentialsException e) {
            log.warn("Not logged in... ");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("not logged in");
        }
    }
}

