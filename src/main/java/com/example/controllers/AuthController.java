package com.example.controllers;

import com.example.interfaces.RoleRepository;
import com.example.interfaces.UserRepository;
import com.example.jwt.JwtUtilities;
import com.example.models.AuthLevel;
import com.example.models.Role;
import com.example.models.UserData;
import com.example.models.payloads.requests.LoginRequest;
import com.example.models.payloads.requests.RegisterRequest;
import com.example.models.payloads.responses.MessageResponse;
import com.example.models.payloads.responses.TokenResponse;
import com.example.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final
    AuthenticationManager testManager;

    private final
    UserRepository userRepository;

    private final
    RoleRepository roleRepository;

    private final
    PasswordEncoder encoder;

    private final
    JwtUtilities jwtUtilities;

    public AuthController(AuthenticationManager testManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtilities jwtUtilities) {
        this.testManager = testManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtilities = jwtUtilities;
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticateUser (@Valid @RequestBody LoginRequest loginRequest) throws ParseException {

        Authentication authentication = testManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtilities.generateToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new TokenResponse(token, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles)/*TokenResponse.builder()
                .token(token)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)*/);

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserData userData = UserData.builder()
                .id(null)
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(encoder.encode(registerRequest.getPassword()))
                .build();



        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByLevel(AuthLevel.LEVEL_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByLevel(AuthLevel.LEVEL_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "distributor" -> {
                        Role modRole = roleRepository.findByLevel(AuthLevel.LEVEL_DISTRIBUTER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByLevel(AuthLevel.LEVEL_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                }
            });
        }

        userData.setRoles(roles);
        userRepository.save(userData);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
