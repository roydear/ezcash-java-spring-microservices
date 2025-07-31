package com.ec.authservice.service;

import com.ec.authservice.dto.RegistrationRequestDTO;
import com.ec.authservice.exception.EmailAlreadyExistsException;
import com.ec.authservice.exception.PhoneNumberAlreadyExistsException;
import com.ec.authservice.grpc.AccountServiceGrpcClient;
import com.ec.authservice.model.User;
import com.ec.authservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountServiceGrpcClient accountServiceGrpcClient;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, AccountServiceGrpcClient accountServiceGrpcClient, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.accountServiceGrpcClient = accountServiceGrpcClient;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByPhoneNumber(String phoneNo) {
        return userRepository.findByPhoneNumber(phoneNo);
    }

    @Transactional
    public void register(RegistrationRequestDTO requestDTO) {
        if (userRepository.findByPhoneNumber(requestDTO.phoneNumber()).isPresent()) {
            throw new PhoneNumberAlreadyExistsException("Phone Number already exists");
        }

        if (userRepository.findByEmail(requestDTO.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email address already exists");
        }

        User user = new User();
        user.setEmail(requestDTO.email());
        user.setPhoneNumber(requestDTO.phoneNumber());
        user.setPassword(passwordEncoder.encode(requestDTO.password()));
        user.setRole(requestDTO.role());

        User newUser = userRepository.save(user);

        accountServiceGrpcClient.createUserAccount(
                String.valueOf(newUser.getId()),
                requestDTO.firstName(),
                requestDTO.lastName(),
                requestDTO.address(),
                String.valueOf(requestDTO.dateOfBirth())
        );
    }
}
