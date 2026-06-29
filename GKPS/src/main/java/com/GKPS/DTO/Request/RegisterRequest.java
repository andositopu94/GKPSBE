package com.GKPS.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String fullName;
    private String gerejaId;
    private String gerejaName;
    private String phoneNumber;
}
