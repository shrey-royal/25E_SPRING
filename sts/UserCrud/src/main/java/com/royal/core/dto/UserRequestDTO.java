package com.royal.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
	@NotBlank
	private String name;
	
	@Email
	@NotBlank
	private String email;
	
	@Size(min = 8)
	private String password;
}
