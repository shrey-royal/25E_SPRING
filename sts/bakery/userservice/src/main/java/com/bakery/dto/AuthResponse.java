package com.bakery.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
	
    private String token;
    
    @Builder.Default
    private String tokenType = "Bearer";
    
    public AuthResponse(String token) {
    	this.token = token;
    }
}
