package com.royal.core.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
	@Id @GeneratedValue
	private Long id;
	
	@NotNull(message = "name is mandatory")
	private String name;
	
	@DecimalMin(value = "1", inclusive = true, message = "age should be in between 1 to 100")
	@DecimalMax(value = "100", inclusive = true, message = "age should be in between 1 to 100")
	@NotNull(message = "age is mandatory")
	private int age;
	
	@Email @NotNull(message = "email is mandatory")
	private String email;
	
	@NotNull(message = "password is mandatory")
	private String password;
	
	@Column(name = "profile_image_url")
	private String profileImageUrl;
}
