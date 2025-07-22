package com.ec.authservice.dto;

public record LoginResponseDTO(
        String token
) {
    public LoginResponseDTO(String token) {
        this.token = token;
    }
}
