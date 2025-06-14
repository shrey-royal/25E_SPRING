package com.royal.core.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class Product {
	private int id;
	
	@NotBlank(message = "Name is mandatory")
	private String name;
	
	@PositiveOrZero(message = "Quantity must be non-negative")
	private int quantity;
	
	@DecimalMin(value = "0.0", inclusive = false,  message = "Price must be positive")
	private double price;
}
