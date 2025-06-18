package com.familymeal.service;

import com.familymeal.entity.User;
import com.familymeal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;

    @Autowired
    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.userRepository = userRepository;
    }

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.verification-url}")
    private String verificationUrl;

    public void sendVerificationEmail(User user) {
        String token = UUID.randomUUID().toString();
        user.setEmailVerificationToken(token);
        user.setEmailVerified(false);
        userRepository.save(user);

        Context context = new Context();
        context.setVariable("name", user.getFirstName());
        String fullVerificationUrl = verificationUrl.trim() + "?token=" + token;
        context.setVariable("verificationUrl", fullVerificationUrl);

        String emailContent = templateEngine.process("verification-email", context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(user.getEmail());
            helper.setSubject("Verify your email address");
            helper.setText(emailContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    public void sendOtpEmail(String email, String otp) {
        Context context = new Context();
        context.setVariable("otp", otp);

        String emailContent = templateEngine.process("otp-email", context);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject("Your OTP for Family Meal Planner");
            helper.setText(emailContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email", e);
        }
    }
} 