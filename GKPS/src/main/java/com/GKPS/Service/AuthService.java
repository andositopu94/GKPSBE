package com.GKPS.Service;

import com.GKPS.Model.CustomUserDetails;
import com.GKPS.Config.JwtService;
import com.GKPS.DTO.Response.AuthResponse;
import com.GKPS.DTO.Request.LoginRequest;
import com.GKPS.DTO.Request.RegisterRequest;
import com.GKPS.Model.Enum.RoleType;
import com.GKPS.Model.User;
import com.GKPS.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {
    private final static Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            logger.warn("Registration failed: Username {} already exists", request.getUsername());
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Registration failed: Email {} already exists", request.getEmail());
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setGerejaId(request.getGerejaId());
        user.setGerejaName(request.getGerejaName());
        user.setEnabled(true);

        Set<RoleType> roles = new HashSet<>();
        roles.add(RoleType.ANGGOTA);
        user.setRoles(roles);

        user.prePersist();

        User savedUser = userRepository.save(user);
        logger.info("User registered successfully: {}", savedUser.getUsername());

        CustomUserDetails userDetails = new CustomUserDetails(savedUser);
        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return buildAuthResponse(savedUser, jwtToken, refreshToken);
    }

    public Optional<AuthResponse> getCurrentUser(String username) {
        return userRepository.findByUsername(username)
                .map(user -> buildAuthResponse(user, null, null));

    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        user.setLastLogin(LocalDateTime.now());
        user.preUpdate();
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        logger.info("User logged in successfully: {}", user.getUsername());

        return buildAuthResponse(user, jwtToken, refreshToken);
    }

    public AuthResponse refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);

        if (username == null) {
            logger.warn("Refresh token failed: Invalid token");
            throw new RuntimeException("Invalid refresh token");
        }

        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            logger.warn("Refresh token failed: User {} not found", username);
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        CustomUserDetails userDetails = new CustomUserDetails(user);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            logger.warn("Refresh token failed: Invalid token for user {}", username);
            throw new RuntimeException("Invalid refresh token");
        }

        String newJwtToken = jwtService.generateToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        return buildAuthResponse(user, newJwtToken, newRefreshToken);
    }

    public void logout(String username) {
        // Implement logout logic if needed (e.g., invalidate tokens, clear sessions)
        logger.info("User logged out: {}", username);
        SecurityContextHolder.clearContext();
    }

    private AuthResponse buildAuthResponse(User user, String token, String refreshToken) {
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setRefreshToken(refreshToken);
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setRoles(user.getRoles());
        response.setGerejaId(user.getGerejaId());
        response.setGerejaName(user.getGerejaName());
        response.setExpiresIn(jwtService.getJwtExpirationMs());
        return response;
    }

}
