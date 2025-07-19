package com.royal.core.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.royal.core.dto.UserRequestDTO;
import com.royal.core.dto.UserResponseDTO;
import com.royal.core.entity.User;
import com.royal.core.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository repository;
	private final Cloudinary cloudinary;
	
	@Override
	public UserResponseDTO createUser(UserRequestDTO dto, MultipartFile image) throws IOException {
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		
		if (image != null && !image.isEmpty()) {
			Map<?, ?> uploadResultMap = cloudinary.uploader().upload(image.getBytes(), Map.of());
			user.setProfileImageUrl((String) uploadResultMap.get("secure_url"));
		}
		
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
				.orElseThrow(() -> new RuntimeException("User not found!"));
	}
	
	@Override
	public UserResponseDTO updateUser(Long id, UserRequestDTO dto, MultipartFile image) throws IOException {
		User user = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("User not found!"));
		
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		
		if (image != null && !image.isEmpty()) {
			Map<?, ?> uploadResultMap = cloudinary.uploader().upload(image.getBytes(), Map.of());
			user.setProfileImageUrl((String) uploadResultMap.get("secure_url"));
		}
		
		return mapToDTO(repository.save(user));
	}
	@Override
	public void deleteUser(Long id) {
		repository.deleteById(id);
	}
	
	private UserResponseDTO mapToDTO(User user) {
		UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setProfileImageUrl(user.getProfileImageUrl());
        return dto;
	}
}
