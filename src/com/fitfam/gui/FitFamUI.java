package com.fitfam.gui;

import com.fitfam.model.FamilyMember;
import com.fitfam.model.Challenge;
import com.fitfam.service.FitnessService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Map;

public class FitFamUI extends JFrame {
    private FitnessService service;
    private JPanel cardContainer;
    private JPanel challengeContainer;

    public FitFamUI(FitnessService service) {
        this.service = service;
        setTitle("FitFam - Family Fitness Dashboard");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        getContentPane().setBackground(new Color(240, 242, 245));

        // --- Header Section ---
        JPanel topPanel = new JPanel(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(45, 106, 79));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        JLabel title = new JLabel("    FAMILY FITNESS DASHBOARD", JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        headerPanel.add(title, BorderLayout.WEST);
        
        // --- Challenge/Quest Section (The Segmented Bars) ---
        challengeContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 15));
        challengeContainer.setBackground(Color.WHITE);
        challengeContainer.setPreferredSize(new Dimension(0, 160));
        challengeContainer.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            "ACTIVE TEAM CHALLENGES",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 12),
            new Color(45, 106, 79)
        ));
        
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(challengeContainer, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // --- Dashboard Container (Leaderboard) ---
        cardContainer = new JPanel(new GridLayout(0, 2, 20, 20));
        cardContainer.setBackground(new Color(240, 242, 245));
        cardContainer.setBorder(new EmptyBorder(20, 30, 20, 30));

        refreshDashboard();

        add(new JScrollPane(cardContainer), BorderLayout.CENTER);

        // --- Action Bar ---
        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionBar.setBackground(Color.WHITE);
        JButton btnLog = new JButton("Update Activities");
        btnLog.setPreferredSize(new Dimension(180, 40));
        btnLog.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLog.addActionListener(e -> showUpdateDialog());
        
        actionBar.add(btnLog);
        add(actionBar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showUpdateDialog() {
        String[] names = service.getMembers().stream().map(FamilyMember::getName).toArray(String[]::new);
        JComboBox<String> nameList = new JComboBox<>(names);
        JTextField stepsField = new JTextField("1000");

        Object[] message = { "Who finished a walk?", nameList, "How many steps?", stepsField };

        int option = JOptionPane.showConfirmDialog(this, message, "Log Activity", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                int index = nameList.getSelectedIndex();
                int steps = Integer.parseInt(stepsField.getText());
                
                service.logActivity(index, steps);
                refreshDashboard();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshDashboard() {
        cardContainer.removeAll();
        for (FamilyMember member : service.getLeaderboard()) {
            cardContainer.add(createMemberCard(member));
        }

        challengeContainer.removeAll();
        for (Challenge c : service.getActiveChallenges()) {
            challengeContainer.add(createChallengeCard(c));
        }

        cardContainer.revalidate();
        cardContainer.repaint();
        challengeContainer.revalidate();
        challengeContainer.repaint();
    }

    private JPanel createChallengeCard(Challenge c) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setPreferredSize(new Dimension(500, 100));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(240, 240, 240)),
            new EmptyBorder(10, 10, 10, 10)
        ));

        JLabel title = new JLabel(c.getEmoji() + " " + c.getTitle() + " (" + c.getCategory() + ")");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        card.add(title, BorderLayout.NORTH);

        // --- Custom Segmented Progress Bar ---
        JPanel bar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int w = getWidth();
                int h = getHeight();
                int currentX = 0;

                // Draw segments for each contributor
                for (Map.Entry<String, Integer> entry : c.getContributions().entrySet()) {
                    double portion = (double) entry.getValue() / c.getTarget();
                    int segmentWidth = (int) (portion * w);
                    
                    g2.setColor(c.getMemberColors().get(entry.getKey()));
                    g2.fillRect(currentX, 0, segmentWidth, h);
                    currentX += segmentWidth;
                }

                // Draw background for remaining part
                g2.setColor(new Color(235, 235, 235));
                g2.fillRect(currentX, 0, w - currentX, h);
                
                // Draw border around the bar
                g2.setColor(new Color(200, 200, 200));
                g2.drawRect(0, 0, w - 1, h - 1);
            }
        };
        bar.setPreferredSize(new Dimension(0, 30));
        card.add(bar, BorderLayout.CENTER);

        JLabel status = new JLabel(String.format("%,d / %,d Total", c.getTotalProgress(), c.getTarget()));
        status.setFont(new Font("SansSerif", Font.PLAIN, 12));
        status.setHorizontalAlignment(JLabel.RIGHT);
        card.add(status, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createMemberCard(FamilyMember m) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(225, 225, 225), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JPanel accent = new JPanel();
        accent.setBackground(m.getColor());
        accent.setPreferredSize(new Dimension(8, 0));
        card.add(accent, BorderLayout.WEST);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        centerPanel.setOpaque(false);
        
        JLabel nameLabel = new JLabel(m.getEmoji() + " " + m.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        
        JLabel scoreLabel = new JLabel("Points: " + String.format("%,d", m.getPoints()));
        
        double progress = (m.getSteps() / 10000.0) * 100;
        String goalStatus = (progress >= 100) ? "DAILY GOAL MET! 🎉" : (int)progress + "% of Daily Goal";
        JLabel progressLabel = new JLabel(goalStatus);
        progressLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));
        progressLabel.setForeground(m.getSteps() >= 10000 ? new Color(45, 106, 79) : Color.GRAY);

        centerPanel.add(nameLabel);
        centerPanel.add(scoreLabel);
        centerPanel.add(progressLabel);
        card.add(centerPanel, BorderLayout.CENTER);

        JPanel stats = new JPanel(new GridLayout(2, 1));
        stats.setOpaque(false);
        JLabel steps = new JLabel("Steps: " + String.format("%,d", m.getSteps()));
        steps.setHorizontalAlignment(JLabel.RIGHT);
        JLabel cals = new JLabel("Burned: " + m.getCalories() + " kcal");
        cals.setHorizontalAlignment(JLabel.RIGHT);
        stats.add(steps);
        stats.add(cals);
        card.add(stats, BorderLayout.EAST);

        return card;
    }
}