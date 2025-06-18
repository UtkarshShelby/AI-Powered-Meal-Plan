package com.familymeal.service;

import com.familymeal.dto.SignUpRequest;
import com.familymeal.entity.User;
import com.familymeal.exception.ResourceNotFoundException;
import com.familymeal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private  UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setEmailVerified(false);

        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new ResourceNotFoundException("User not found");
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    public void verifyEmail(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new RuntimeException("Verification token cannot be empty");
        }

        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired verification token"));

        if (user.isEmailVerified()) {
            throw new RuntimeException("Email is already verified");
        }

        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
} 