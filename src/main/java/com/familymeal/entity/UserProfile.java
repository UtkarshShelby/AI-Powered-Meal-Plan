package com.familymeal.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double weight; // in kgs

    @Column(nullable = false)
    private Double height; // in cms

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private Integer age;

    @Column(name = "daily_calorie_requirement")
    private Integer dailyCalorieRequirement;

    @Column(name = "dietary_preference")
    @Enumerated(EnumType.STRING)
    private DietaryPreference dietaryPreference;

    @Column(name = "allergies", length = 1000)
    private String allergies; // Comma-separated list of allergies

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    @Column(name = "lifestyle")
    private String lifestyle;

    public String getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(String lifestyle) {
        this.lifestyle = lifestyle;
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

    public Integer getDailyCalorieRequirement() {
        return dailyCalorieRequirement;
    }

    public void setDailyCalorieRequirement(Integer dailyCalorieRequirement) {
        this.dailyCalorieRequirement = dailyCalorieRequirement;
    }

    public DietaryPreference getDietaryPreference() {
        return dietaryPreference;
    }

    public void setDietaryPreference(DietaryPreference dietaryPreference) {
        this.dietaryPreference = dietaryPreference;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
} 