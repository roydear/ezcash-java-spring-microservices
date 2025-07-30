package com.ec.authservice.service;

import com.ec.authservice.dto.RegistrationRequestDTO;
import com.ec.authservice.exception.EmailAlreadyExistsException;
import com.ec.authservice.exception.PhoneNumberAlreadyExistsException;
import com.ec.authservice.grpc.AccountServiceGrpcClient;
import com.ec.authservice.model.User;
import com.ec.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountServiceGrpcClient accountServiceGrpcClient;


    public UserService(UserRepository userRepository, AccountServiceGrpcClient accountServiceGrpcClient) {
        this.userRepository = userRepository;
        this.accountServiceGrpcClient = accountServiceGrpcClient;
    }

    public Optional<User> findByPhoneNumber(String phoneNo) {
        return userRepository.findByPhoneNumber(phoneNo);
    }

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
        user.setPassword(requestDTO.password());
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
