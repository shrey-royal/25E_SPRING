package com.royal.core.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.royal.core.dto.UserRequestDTO;
import com.royal.core.dto.UserResponseDTO;

public interface UserService {
	UserResponseDTO createUser(UserRequestDTO dto, MultipartFile image) throws IOException;
	List<UserResponseDTO> getAllUsers();
	UserResponseDTO getUserById(Long id);
	UserResponseDTO updateUser(Long id, UserRequestDTO dto, MultipartFile image) throws IOException;
	void deleteUser(Long id);
}
