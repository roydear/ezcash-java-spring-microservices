package com.ec.authservice.controller;

import com.ec.authservice.dto.RegistrationRequestDTO;
import com.ec.authservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a user")
    @PostMapping("/registration")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequestDTO requestDTO) {
        userService.register(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
