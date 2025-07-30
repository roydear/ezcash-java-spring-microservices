package com.ec.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.UUID;

public record RegistrationRequestDTO(
        @Email(message = "Email should be valid")
        @NotBlank(message = "Email is required")
        String email,

        @Pattern(regexp="(^$|[0-9]{11})",message = "Mobile number must be 11 digits")
        @NotBlank(message = "Phone No. is required")
        String phoneNumber,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Role is required")
        String role,

        @NotBlank(message = "First Name is required")
        String firstName,

        @NotBlank(message = "Last Name is required")
        String lastName,

        @NotBlank(message = "Address is required")
        String address,

        @NotBlank(message = "Date of Birth is required")
        String dateOfBirth
) {
}
// create a validation
