package com.GKPS.Controller;

import com.GKPS.DTO.Response.AuthResponse;
import com.GKPS.DTO.Request.LoginRequest;
import com.GKPS.DTO.Request.RefreshTokenRequest;
import com.GKPS.DTO.Request.RegisterRequest;
import com.GKPS.Service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class AuthController {
    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("Register request received for username: {}", request.getUsername());
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        logger.info("Login request received for username: {}", request.getUsernameOrEmail());
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        logger.info("Refresh token request received");
        AuthResponse response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            logger.info("Logout request received for user: {}", userDetails.getUsername());
            authService.logout(userDetails.getUsername());
        }
        return ResponseEntity.ok().body("Logged out successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {

            return ResponseEntity.status(401).build();
        }

        logger.info("Get current user request received for user: {}", userDetails.getUsername());
        return authService.getCurrentUser(userDetails.getUsername())
                .map(currentUser -> ResponseEntity.ok(currentUser))
                .orElse(ResponseEntity.notFound().build());
    }

}
