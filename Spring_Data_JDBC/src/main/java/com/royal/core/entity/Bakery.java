package com.royal.core.entity;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bakery {
	
	@Id
	private Long id;
	
	@NotBlank(message = "Bakery name must not be blank")
	@Size(max = 100, message = "Bakery name must not exceed 100 characters")
	private String name;
	
	@NotBlank(message = "Type name must not be blank")
	@Size(max = 50, message = "Type must not exceed 50 characters")
	private String type;
	
	@PositiveOrZero(message = "Price must be zero or positive")
	private double price;
}