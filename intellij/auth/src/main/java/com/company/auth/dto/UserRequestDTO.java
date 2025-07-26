package com.company.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {
    @Email
    @NotNull(message = "Email is mandatory")
    private String email;

    @NotNull(message = "Username is mandatory")
    private String username;

    @Size(min = 8, message = "Password length > 8")
    private String password;
}
