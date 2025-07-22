package com.ec.authservice.service;

import com.ec.authservice.model.User;
import com.ec.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByPhoneNumber(String phoneNo) {
        return userRepository.findByPhoneNumber(phoneNo);
    }
}
