package com.ec.accountservice.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AccountResponseDTO(UUID id,
                                 UUID userId,
                                 String firstName,
                                 String lastName,
                                 String address,
                                 LocalDate dateOfBirth) {
}
