package com.ec.authservice.dto;

import java.time.LocalDate;
import java.util.UUID;

public record RegistrationRequestDTO(
        String email,
        String phoneNumber,
        String password,
        String role,
        String firstName,
        String lastName,
        String address,
        LocalDate dateOfBirth
) {
}
