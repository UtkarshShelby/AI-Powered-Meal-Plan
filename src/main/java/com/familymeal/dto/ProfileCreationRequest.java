package com.familymeal.dto;

import com.familymeal.entity.DietaryPreference;
import javax.validation.constraints.*;
import java.util.List;

public class ProfileCreationRequest {
    @NotNull(message = "Weight is required")
    @Min(value = 20, message = "Weight must be at least 20 kg")
    @Max(value = 300, message = "Weight must be less than 300 kg")
    private Double weight;

    @NotNull(message = "Height is required")
    @Min(value = 100, message = "Height must be at least 100 cm")
    @Max(value = 250, message = "Height must be less than 250 cm")
    private Double height;

    @NotNull(message = "Gender is required")
    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
    private String gender;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 120, message = "Age must be less than 120")
    private Integer age;

    @NotNull(message = "Dietary preference is required")
    private DietaryPreference dietaryPreference;

    private List<String> allergies;

    // Getters and Setters
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public DietaryPreference getDietaryPreference() {
        return dietaryPreference;
    }

    public void setDietaryPreference(DietaryPreference dietaryPreference) {
        this.dietaryPreference = dietaryPreference;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
} 