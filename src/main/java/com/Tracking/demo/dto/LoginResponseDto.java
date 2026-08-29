package com.Tracking.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginResponseDto {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String token;
}