package com.bakery.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bakery.dto.UserRequest;
import com.bakery.dto.UserResponse;
import com.bakery.entity.User;
import com.bakery.enums.Role;
import com.bakery.mapper.UserMapper;
import com.bakery.repository.UserRepository;
import com.bakery.service.UserService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public UserResponse register(UserRequest request, MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                        ObjectUtils.asMap("folder", "bakery_users"));
                request.setImageUrl((String) uploadResult.get("secure_url"));
            }
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }

        if (request.getRole() == null) {
            request.setRole(Role.ROLE_USER);
        }

        User savedUser = userRepository.save(UserMapper.toEntity(request));
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password)) // ⚠️ in prod use BCrypt
                .map(UserMapper::toResponse)
                .orElse(null);
    }

}
