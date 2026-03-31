package com.fitfam.service;

import com.fitfam.model.FamilyMember;
import com.fitfam.model.Challenge;
import java.util.*;
import java.awt.Color;

public class FitnessService {
    private List<FamilyMember> members = new ArrayList<>();
    private List<Challenge> activeChallenges = new ArrayList<>();

    public FitnessService() {
        // --- 1. Initialize Family Members ---
        members.add(new FamilyMember("Dad", "Admin", "👨", 12450, 520, 980, new Color(0x2D6A4F)));
        members.add(new FamilyMember("Mom", "Member", "👩", 10200, 480, 860, new Color(0x52B788)));
        members.add(new FamilyMember("Alex", "Member", "🧒", 8750, 340, 720, new Color(0xFF6B35)));
        members.add(new FamilyMember("Jamie", "Member", "👦", 6300, 280, 560, new Color(0xFFB830)));

        // --- 2. Initialize Challenges ---
        // Using the new Map-based Challenge constructor
        activeChallenges.add(new Challenge(
            "Family 50K", "Total team steps", "🏃", "Steps", 50000
        ));
        
        activeChallenges.add(new Challenge(
            "Calorie Crushers", "Total family calories burned", "🔥", "Calories", 2500
        ));
    }

    // --- Member Logic ---
    public List<FamilyMember> getMembers() { return members; }

    public List<FamilyMember> getLeaderboard() {
        List<FamilyMember> sorted = new ArrayList<>(members);
        sorted.sort((a, b) -> b.getPoints() - a.getPoints());
        return sorted;
    }

    public void logActivity(int index, int steps) {
        if (index >= 0 && index < members.size()) {
            FamilyMember member = members.get(index);
            
            // Calculate calories before and after to get the "contribution"
            int caloriesBefore = member.getCalories();
            member.addActivity(steps);
            int caloriesGained = member.getCalories() - caloriesBefore;
            
            // Update the multi-color challenge bars
            updateChallengeContributions(member, steps, caloriesGained);
        }
    }

    // --- Challenge Logic ---
    public List<Challenge> getActiveChallenges() { 
        return activeChallenges; 
    }

    private void updateChallengeContributions(FamilyMember member, int steps, int calories) {
        for (Challenge c : activeChallenges) {
            if (c.getCategory().equalsIgnoreCase("Steps")) {
                // This adds a colored segment for the specific member
                c.addContribution(member.getName(), steps, member.getColor());
            } 
            else if (c.getCategory().equalsIgnoreCase("Calories")) {
                c.addContribution(member.getName(), calories, member.getColor());
            }
        }
    }
}