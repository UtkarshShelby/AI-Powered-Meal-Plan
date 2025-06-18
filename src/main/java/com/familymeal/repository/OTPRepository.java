package com.familymeal.repository;

import com.familymeal.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
 
public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByEmailAndOtpValueAndIsUsedFalse(String email, String otpValue);
    void deleteByEmail(String email);
} 