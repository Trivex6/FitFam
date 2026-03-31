package com.fitfam.model;

import java.awt.Color;
import java.util.*;

public class Challenge {
    private String title, description, emoji, category;
    private int target;
    private boolean completed;
    
    // New: Tracks how much each family member contributed
    private Map<String, Integer> contributions = new LinkedHashMap<>();
    // New: Stores the color of each member for the UI bar
    private Map<String, Color> memberColors = new HashMap<>();

    public Challenge(String title, String description, String emoji, String category, int target) {
        this.title = title;
        this.description = description;
        this.emoji = emoji;
        this.category = category;
        this.target = target;
        this.completed = false;
    }

    // --- Core Logic Methods ---

    /**
     * Updates individual contribution. 
     * This is what allows the UI to show different colors in the progress bar.
     */
    public void addContribution(String name, int amount, Color color) {
        int current = contributions.getOrDefault(name, 0);
        contributions.put(name, Math.min(target, current + amount));
        memberColors.put(name, color);
        
        if (getTotalProgress() >= target) {
            completed = true;
        }
    }

    public int getTotalProgress() {
        return contributions.values().stream().mapToInt(Integer::intValue).sum();
    }

    public float getPercent() {
        return Math.min(1f, (float) getTotalProgress() / target);
    }

    // --- Getters ---

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getEmoji() { return emoji; }
    public String getCategory() { return category; }
    public int getTarget() { return target; }
    public boolean isCompleted() { return completed; }
    
    // Needed for the UI to draw the segmented bar
    public Map<String, Integer> getContributions() { return contributions; }
    public Map<String, Color> getMemberColors() { return memberColors; }
}