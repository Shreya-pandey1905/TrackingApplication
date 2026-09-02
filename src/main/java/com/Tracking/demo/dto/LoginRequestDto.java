package com.Tracking.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginRequestDto {

    @Size(min = 3, max = 50)
    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

}
