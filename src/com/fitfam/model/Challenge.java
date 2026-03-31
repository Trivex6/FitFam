package com.fitfam.model;

import java.awt.Color;

public class Challenge {
    private String title, description, emoji, category;
    private int progress, target, participants;
    private boolean completed;
    private Color color;

    public Challenge(String title, String description, String emoji, String category,
                    int progress, int target, int participants, Color color) {
        this.title = title; 
        this.description = description;
        this.emoji = emoji; 
        this.category = category;
        this.progress = progress; 
        this.target = target;
        this.participants = participants; 
        this.color = color;
    }

    // --- Getters ---
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getEmoji() { return emoji; }
    public String getCategory() { return category; }
    public int getProgress() { return progress; }
    public int getTarget() { return target; }
    public int getParticipants() { return participants; }
    public boolean isCompleted() { return completed; }
    public Color getColor() { return color; }

    // --- Logic Methods ---
    public float getPercent() { 
        return Math.min(1f, (float) progress / target); 
    }

    public void setCompleted(boolean b) { 
        this.completed = b; 
    }

    public void incrementProgress(int v) { 
        this.progress = Math.min(target, progress + v); 
    }
}