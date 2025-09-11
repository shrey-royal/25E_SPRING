package com.bakery.dto;

import com.bakery.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
	private String name;
	private String email;
	private String password;
	private Role role;
	private String imageUrl;
}
