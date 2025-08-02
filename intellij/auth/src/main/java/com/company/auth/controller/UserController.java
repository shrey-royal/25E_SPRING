package com.company.auth.controller;

import com.company.auth.dto.UserRequestDTO;
import com.company.auth.dto.UserResponseDTO;
import com.company.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO user) {
        return ResponseEntity.ok(service.saveUser(user));
    }

//    @GetMapping
//    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
//        return ResponseEntity.ok(service.getAllUsers());
//    }
}
