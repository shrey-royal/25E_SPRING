package com.bakery.mapper;

import com.bakery.dto.UserRequest;
import com.bakery.dto.UserResponse;
import com.bakery.entity.User;

public class UserMapper {
	public static User toEntity(UserRequest request) {
        if (request == null) return null;
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .imageUrl(request.getImageUrl())
                .build();
    }

    public static UserResponse toResponse(User user) {
        if (user == null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .imageUrl(user.getImageUrl())
                .build();
    }
}
