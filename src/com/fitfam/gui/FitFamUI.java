package com.fitfam.gui;

import com.fitfam.model.FamilyMember;
import com.fitfam.service.FitnessService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class FitFamUI extends JFrame {
    private FitnessService service;
    private JPanel cardContainer;

    public FitFamUI(FitnessService service) {
        this.service = service;
        setTitle("FitFam - Family Fitness Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(240, 242, 245));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(45, 106, 79));
        headerPanel.setPreferredSize(new Dimension(0, 80));
        JLabel title = new JLabel("  FAMILY FITNESS DASHBOARD", JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerPanel.add(title, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Dashboard Container
        cardContainer = new JPanel(new GridLayout(0, 2, 20, 20));
        cardContainer.setBackground(new Color(240, 242, 245));
        cardContainer.setBorder(new EmptyBorder(20, 30, 20, 30));

        refreshDashboard();

        add(new JScrollPane(cardContainer), BorderLayout.CENTER);

        // Action Bar
        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionBar.setBackground(Color.WHITE);
        JButton btnLog = new JButton("Update Activities");
        btnLog.setPreferredSize(new Dimension(180, 40));
        
        // --- THIS IS THE NEW INTERACTIVE PART ---
        btnLog.addActionListener(e -> showUpdateDialog());
        
        actionBar.add(btnLog);
        add(actionBar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showUpdateDialog() {
    String[] names = service.getMembers().stream().map(FamilyMember::getName).toArray(String[]::new);
    JComboBox<String> nameList = new JComboBox<>(names);
    JTextField stepsField = new JTextField("1000"); // Default jump

    Object[] message = {
        "Who finished a walk?", nameList,
        "How many steps?", stepsField
    };

    int option = JOptionPane.showConfirmDialog(this, message, "Log Activity", JOptionPane.OK_CANCEL_OPTION);
    
    if (option == JOptionPane.OK_OPTION) {
        try {
            int index = nameList.getSelectedIndex();
            int steps = Integer.parseInt(stepsField.getText());
            
            // The magic happens here: Service updates the model
            service.getMembers().get(index).addActivity(steps);
            
            refreshDashboard();
            
            // Feedback to make it "fun"
            String name = (String)nameList.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Boom! " + name + " just crushed " + steps + " steps!");
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a number, Alex!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private void refreshDashboard() {
        cardContainer.removeAll();
        for (FamilyMember member : service.getLeaderboard()) {
            cardContainer.add(createMemberCard(member));
        }
        cardContainer.revalidate();
        cardContainer.repaint();
    }

    private JPanel createMemberCard(FamilyMember m) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
            new EmptyBorder(15, 0, 15, 20)
        ));

        JPanel accent = new JPanel();
        accent.setBackground(m.getColor());
        accent.setPreferredSize(new Dimension(8, 0));
        card.add(accent, BorderLayout.WEST);

        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setOpaque(false);
        JLabel nameLabel = new JLabel(m.getEmoji() + " " + m.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel pointsLabel = new JLabel("Score: " + m.getPoints() + " pts");
        info.add(nameLabel);
        info.add(pointsLabel);
        card.add(info, BorderLayout.CENTER);

        JPanel stats = new JPanel(new GridLayout(2, 1));
        stats.setOpaque(false);
        stats.add(new JLabel("Steps: " + String.format("%,d", m.getSteps())));
        stats.add(new JLabel("Burned: " + m.getCalories() + " kcal"));
        card.add(stats, BorderLayout.EAST);

        // Progress toward 10k steps goal
        double progress = (m.getSteps() / 10000.0) * 100;
        String goalStatus = (progress >= 100) ? "GOAL REACHED! 🎉" : (int)progress + "% of Daily Goal";

        JLabel progressLabel = new JLabel(goalStatus);
        progressLabel.setForeground(m.getSteps() >= 10000 ? new Color(45, 106, 79) : Color.GRAY);
        progressLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        // Add this label to your card's South or Center panel

        return card;
    }
}