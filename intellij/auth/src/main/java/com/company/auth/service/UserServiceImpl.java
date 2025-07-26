package com.company.auth.service;

import com.company.auth.dto.UserRequestDTO;
import com.company.auth.dto.UserResponseDTO;
import com.company.auth.entity.User;
import com.company.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO saveUser(UserRequestDTO userDTO) {
        User user = mapToEntity(userDTO);
        return mapToDTO(repository.save(user));
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("user with id("+id+") not found"));
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        return repository.findByUsername(username)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("user with username("+username+") not found"));
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("user with email("+email+") not found"));
    }

    @Override
    public void deleteUserById(Long id) {
        repository.deleteById(id);
    }

    private User mapToEntity(UserRequestDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return user;
    }

    private UserResponseDTO mapToDTO(User user) {
        UserResponseDTO userDTO = new UserResponseDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
}
