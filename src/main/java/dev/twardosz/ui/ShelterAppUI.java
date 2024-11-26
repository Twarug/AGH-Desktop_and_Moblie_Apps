package dev.twardosz.ui;

import dev.twardosz.*;

import javax.swing.*;
import java.awt.*;

public class ShelterAppUI {
    private final JFrame frame;

    public ShelterAppUI(ShelterManager manager) {
        frame = new JFrame("Animal Shelter Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel login = new LoginPanel(frame, manager);
        frame.add(login, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
