package com.familymeal.service;

import com.familymeal.dto.ProfileCreationRequest;
import com.familymeal.entity.User;
import com.familymeal.entity.UserProfile;
import com.familymeal.exception.ResourceNotFoundException;
import com.familymeal.repository.UserProfileRepository;
import com.familymeal.repository.UserRepository;
import com.familymeal.util.CalorieCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserProfile createProfile(Long userId, ProfileCreationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (userProfileRepository.existsByUserId(userId)) {
            throw new IllegalStateException("Profile already exists for this user");
        }

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setWeight(request.getWeight());
        profile.setHeight(request.getHeight());
        profile.setGender(request.getGender());
        profile.setAge(request.getAge());
        profile.setDietaryPreference(request.getDietaryPreference());
        
        if (request.getAllergies() != null && !request.getAllergies().isEmpty()) {
            profile.setAllergies(String.join(",", request.getAllergies()));
        }

        // Calculate and set daily calorie requirement
        int dailyCalories = CalorieCalculator.calculateDailyCalories(
            request.getWeight(),
            request.getHeight(),
            request.getAge(),
            request.getGender(),
            request.getLifestyle() // Default lifestyle since it's not in the request
        );
        profile.setDailyCalorieRequirement(dailyCalories);

        return userProfileRepository.save(profile);
    }

    public UserProfile getProfile(Long userId) {
        return userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }
} 