package com.bakery.dto;

import com.bakery.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

	@NotBlank(message="username is blank")
	@Min(value = 8, message = "min length of user is 8")
	private String username;
	
	@Email
	private String email;
	
	@Min(value = 8)
	private String password;
	
	@NotBlank
	private String imageUrl;
	
	@NotBlank
	private Enum<Role> role;
}
