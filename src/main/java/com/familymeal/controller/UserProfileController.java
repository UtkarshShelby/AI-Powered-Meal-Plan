package com.familymeal.controller;

import com.familymeal.dto.ProfileCreationRequest;
import com.familymeal.entity.User;
import com.familymeal.entity.UserProfile;
import com.familymeal.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.familymeal.security.UserPrincipal;


import javax.validation.Valid;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {
    private  UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfile> createProfile(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody ProfileCreationRequest request) {
                System.out.println("incoming request: " + request);
                Long userId = userPrincipal.getId();
        UserProfile profile = userProfileService.createProfile(userId, request);
        return ResponseEntity.ok(profile);
    }

    @GetMapping

    public ResponseEntity<UserProfile> getProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        // @AuthenticationPrincipal UserDetails userDetails
        Long userId = userPrincipal.getId();
        UserProfile profile = userProfileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }
} 