package com.gameflix.auth.controller;

import com.gameflix.auth.dto.UserDTO;
import com.gameflix.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class AuthController {
    private final UserService svc;
    public AuthController(UserService svc) { this.svc = svc; }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO dto) {
        if (dto.getUsername().isBlank() || dto.getPassword().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message","Username and password must not be empty"));
        }
        if (!svc.register(dto.getUsername(), dto.getPassword())) {
            return ResponseEntity.status(409)
                    .body(Map.of("message","Username already exists"));
        }
        return ResponseEntity.ok(Map.of("message","User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO dto) {
        if (svc.login(dto.getUsername(), dto.getPassword())) {
            return ResponseEntity.ok(Map.of("message","Login successful"));
        }
        return ResponseEntity.status(401)
                .body(Map.of("message","Invalid username or password"));
    }
}