package com.familymeal.dto;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class FamilyMemberDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Relationship is required")
    private String relationship;

    private List<String> dietaryRestrictions;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public List<String> getDietaryRestrictions() {
        return dietaryRestrictions;
    }

    public void setDietaryRestrictions(List<String> dietaryRestrictions) {
        this.dietaryRestrictions = dietaryRestrictions;
    }
} 