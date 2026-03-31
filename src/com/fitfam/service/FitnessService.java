package com.fitfam.service;

import com.fitfam.model.FamilyMember;
import java.util.*;
import java.awt.Color;

public class FitnessService {
    private List<FamilyMember> members = new ArrayList<>();

    public FitnessService() {
        // Updated with your specific hex colors and emojis
        members.add(new FamilyMember("Dad", "Admin", "👨", 12450, 520, 980, new Color(0x2D6A4F)));
        members.add(new FamilyMember("Mom", "Member", "👩", 10200, 480, 860, new Color(0x52B788)));
        members.add(new FamilyMember("Alex", "Member", "🧒", 8750, 340, 720, new Color(0xFF6B35)));
        members.add(new FamilyMember("Jamie", "Member", "👦", 6300, 280, 560, new Color(0xFFB830)));
    }

    public List<FamilyMember> getMembers() { return members; }

    public List<FamilyMember> getLeaderboard() {
        List<FamilyMember> sorted = new ArrayList<>(members);
        sorted.sort((a, b) -> b.getPoints() - a.getPoints());
        return sorted;
    }

    public void logActivity(int index, int steps) { // Remove "int calories" here
    if (index >= 0 && index < members.size()) {
        // Only pass the steps! The FamilyMember class handles the math now.
        members.get(index).addActivity(steps); 
    }
  }
}