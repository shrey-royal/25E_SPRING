package com.bakery.service;

import org.springframework.web.multipart.MultipartFile;

import com.bakery.dto.UserRequest;
import com.bakery.dto.UserResponse;

public interface UserService {
	UserResponse register(UserRequest request, MultipartFile file);
    UserResponse login(String email, String password);
}
