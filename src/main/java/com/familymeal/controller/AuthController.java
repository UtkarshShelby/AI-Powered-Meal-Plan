package com.familymeal.controller;

import com.familymeal.dto.*;
import com.familymeal.entity.User;
import com.familymeal.security.JwtTokenProvider;
import com.familymeal.service.EmailService;
import com.familymeal.service.OtpService;
import com.familymeal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final EmailService emailService;
    private final OtpService otpService;

    @Autowired
    public AuthController(UserService userService,
                         JwtTokenProvider tokenProvider,
                         EmailService emailService,
                         OtpService otpService) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.emailService = emailService;
        this.otpService = otpService;
    }
    @GetMapping("/test-log")
    public String testLog() {
        System.out.println(">>> testLog endpoint hit");
        return "Test log success";
    }
    

    @PostMapping("/initiate-signup")
    public ResponseEntity<?> initiateSignup(@Valid @RequestBody InitialSignUpRequest request) {
        // Check if email or phone number already exists
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Email is already registered"));
        }
        if (userService.existsByPhoneNumber(request.getPhoneNumber())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Phone number is already registered"));
        }

        // Generate and send OTP
        otpService.generateAndSendOtp(request.getEmail());

        return ResponseEntity.ok(new ApiResponse(true, "OTP sent successfully"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerificationRequest request) {
        if (!otpService.verifyOtp(request.getEmail(), request.getOtp())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid or expired OTP"));
        }

        return ResponseEntity.ok(new ApiResponse(true, "OTP verified successfully"));
    }

    @PostMapping("/complete-signup")
    public ResponseEntity<?> completeSignup(@Valid @RequestBody SignUpRequest request) {
        // Check if email or phone number already exists
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Email is already registered"));
        }
        if (userService.existsByPhoneNumber(request.getPhoneNumber())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Phone number is already registered"));
        }

        // Create user
        User user = userService.registerUser(request);
        
        // Send verification email
        emailService.sendVerificationEmail(user);

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println(">>> Inside /login endpoint");
        User user = userService.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate and send OTP
        otpService.generateAndSendOtp(loginRequest.getEmail());

        return ResponseEntity.ok(new ApiResponse(true, "OTP sent successfully"));
    }

    @PostMapping("/verify-login")
    public ResponseEntity<?> verifyLogin(@Valid @RequestBody OtpVerificationRequest request) {
        System.out.println(">>> Inside verifyLogin endpoint <<<");
        User user = userService.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!otpService.verifyOtp(request.getEmail(), request.getOtp())) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Invalid or expired OTP"));
        }

        // Generate JWT token
        String jwt = tokenProvider.generateToken(user);
       

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam(required = false) String token) {
        try {
            if (token == null || token.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Verification token is required"));
            }

            userService.verifyEmail(token);
            return ResponseEntity.ok(new ApiResponse(true, "Email verified successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<?> resendVerificationEmail(@RequestParam String email) {
        try {
            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            if (user.isEmailVerified()) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse(false, "Email is already verified"));
            }
            
            emailService.sendVerificationEmail(user);
            return ResponseEntity.ok(new ApiResponse(true, "Verification email sent successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
} 