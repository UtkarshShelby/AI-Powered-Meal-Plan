package com.familymeal.service;

import com.familymeal.model.OTP;
import com.familymeal.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OtpService {
    private final SecureRandom secureRandom = new SecureRandom();
    private final EmailService emailService;
    private final OTPRepository otpRepository;

    @Value("${otp.expiration.minutes}")
    private int otpExpirationMinutes;

    @Autowired
    public OtpService(EmailService emailService, OTPRepository otpRepository) {
        this.emailService = emailService;
        this.otpRepository = otpRepository;
    }

    @Transactional
    public void generateAndSendOtp(String email) {
        String otp = generateOtp();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(otpExpirationMinutes);
        
        // Delete any existing OTPs for this email
        otpRepository.deleteByEmail(email);
        
        // Create and save new OTP
        OTP otpEntity = new OTP();
        otpEntity.setEmail(email);
        otpEntity.setOtpValue(otp);
        otpEntity.setExpiryTime(expirationTime);
        otpEntity.setUsed(false);
        otpRepository.save(otpEntity);
        
        emailService.sendOtpEmail(email, otp);
    }

    @Transactional
    public boolean verifyOtp(String email, String otp) {
        return otpRepository.findByEmailAndOtpValueAndIsUsedFalse(email, otp)
                .map(otpEntity -> {
                    if (LocalDateTime.now().isAfter(otpEntity.getExpiryTime())) {
                        return false;
                    }
                    otpEntity.setUsed(true);
                    otpRepository.save(otpEntity);
                    return true;
                })
                .orElse(false);
    }

    private String generateOtp() {
        return String.format("%06d", secureRandom.nextInt(1000000));
    }
} 