package com.familymeal.util;

public class CalorieCalculator {
    private static final double MALE_BMR_MULTIPLIER = 10.0;
    private static final double FEMALE_BMR_MULTIPLIER = 9.6;
    private static final double WEIGHT_MULTIPLIER = 6.25;
    private static final double HEIGHT_MULTIPLIER = 5.0;
    private static final double AGE_MULTIPLIER = 5.0;
    private static final double SEDENTARY_LIFESTYLE_MULTIPLIER = 1.2;
    private static final double LIGHTLY_ACTIVE_LIFESTYLE_MULTIPLIER = 1.375;
    private static final double MODERATELY_ACTIVE_LIFESTYLE_MULTIPLIER = 1.55;
    private static final double VERY_ACTIVE_LIFESTYLE_MULTIPLIER = 1.725;
    private static final double SUPER_ACTIVE_LIFESTYLE_MULTIPLIER = 1.9;
    
    public static int calculateDailyCalories(double weight, double height, int age, String gender, String lifestyle) {
        double bmr;
        if (gender.equalsIgnoreCase("MALE")) {
            bmr = (MALE_BMR_MULTIPLIER * weight) + (HEIGHT_MULTIPLIER * height) - (AGE_MULTIPLIER * age) + 5;
        } else {
            bmr = (FEMALE_BMR_MULTIPLIER * weight) + (HEIGHT_MULTIPLIER * height) - (AGE_MULTIPLIER * age) - 161;
        }
        
        double lifestyleMultiplier;
        switch (lifestyle.toUpperCase()) {
            case "SEDENTARY":
                lifestyleMultiplier = SEDENTARY_LIFESTYLE_MULTIPLIER;
                break;
            case "LIGHTLY_ACTIVE":
                lifestyleMultiplier = LIGHTLY_ACTIVE_LIFESTYLE_MULTIPLIER;
                break;
            case "MODERATELY_ACTIVE":
                lifestyleMultiplier = MODERATELY_ACTIVE_LIFESTYLE_MULTIPLIER;
                break;
            case "VERY_ACTIVE":
                lifestyleMultiplier = VERY_ACTIVE_LIFESTYLE_MULTIPLIER;
                break;
            case "SUPER_ACTIVE":
                lifestyleMultiplier = SUPER_ACTIVE_LIFESTYLE_MULTIPLIER;
                break;
            default:
                lifestyleMultiplier = SEDENTARY_LIFESTYLE_MULTIPLIER;
        }
        
        return (int) Math.round(bmr * lifestyleMultiplier);
    }
} 