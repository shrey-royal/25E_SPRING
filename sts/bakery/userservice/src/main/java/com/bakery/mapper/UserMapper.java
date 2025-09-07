package com.bakery.mapper;

import com.bakery.dto.UserRequest;
import com.bakery.dto.UserResponse;
import com.bakery.entity.User;
import com.bakery.enums.Role;

public class UserMapper {
    public static User toEntity(UserRequest dto) {
        if (dto == null) return null;
        return User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .imageUrl(dto.getImageUrl())
                .role(dto.getRole() == null ? Role.USER : dto.getRole())
                .build();
    }

    public static UserResponse toDto(User u) {
        if (u == null) return null;
        return UserResponse.builder()
                .id(u.getId())
                .username(u.getUsername())
                .email(u.getEmail())
                .imageUrl(u.getImageUrl())
                .build();
    }
}
