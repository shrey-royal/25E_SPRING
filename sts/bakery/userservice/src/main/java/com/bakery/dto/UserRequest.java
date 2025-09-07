package com.bakery.dto;

import com.bakery.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "username is blank")
    @Size(min = 3, message = "min length of username is 3")
    private String username;

    @Email(message = "invalid email")
    @NotBlank(message = "email is blank")
    private String email;

    @NotBlank(message = "password is blank")
    @Size(min = 8, message = "min length of password is 8")
    private String password;

    private String imageUrl;

    private Role role;
}
