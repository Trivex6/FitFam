package com.fitfam;

import com.fitfam.service.FitnessService;
import com.fitfam.gui.FitFamUI;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // 1. Create the service (the data)
        FitnessService service = new FitnessService();

        // 2. Pass the service into the UI (the display)
        SwingUtilities.invokeLater(() -> {
            new FitFamUI(service); 
        });
    }
}