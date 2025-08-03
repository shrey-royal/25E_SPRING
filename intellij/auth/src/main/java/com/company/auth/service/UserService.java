package com.company.auth.service;

import com.company.auth.dto.UserRequestDTO;
import com.company.auth.dto.UserResponseDTO;

import java.util.List;

public interface UserService {
    boolean authenticate(String username, String password);

    UserResponseDTO saveUser(UserRequestDTO user);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO getUserByUsername(String username);
    UserResponseDTO getUserByEmail(String email);
    void deleteUserById(Long id);
}
