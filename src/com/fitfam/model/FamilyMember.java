package com.fitfam.model;
import java.awt.Color;

public class FamilyMember 
{
    private String name, role, emoji;
    private int steps, calories, points;
    private Color color;

    public FamilyMember(String name, String role, String emoji, int steps, int calories, int points, Color color) {
        this.name = name; this.role = role; this.emoji = emoji;
        this.steps = steps; this.calories = calories;
        this.points = points; this.color = color;
    }

    // Getters
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getEmoji() { return emoji; }
    public int getSteps() { return steps; }
    public int getCalories() { return calories; }
    public int getPoints() { return points; }
    public Color getColor() { return color; }

    // Logic to update data
    public void addActivity(int newSteps) {
    this.steps += newSteps;
    
    // 1000 steps = 40 calories (approx. for average walking)
    int caloriesGained = (newSteps / 1000) * 40; 
    this.calories += caloriesGained;
    
    // Points = 10% of steps + 2x calories
    this.points += (newSteps / 10) + (caloriesGained * 2); 
  }
}