package com.company.auth.controller;

import com.company.auth.dto.LoginRequestDTO;
import com.company.auth.dto.LoginResponseDTO;
import com.company.auth.dto.UserRequestDTO;
import com.company.auth.dto.UserResponseDTO;
import com.company.auth.service.UserService;
import com.company.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService service;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO user) {
        return ResponseEntity.ok(service.saveUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        boolean authenticated = service.authenticate(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

        if (!authenticated) {
            return ResponseEntity.status(401).build();
        }

        String token = jwtUtil.generateToken(loginRequestDTO.getUsername());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
