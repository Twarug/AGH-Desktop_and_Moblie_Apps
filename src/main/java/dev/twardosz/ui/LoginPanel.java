package dev.twardosz.ui;

import dev.twardosz.ShelterManager;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final JTextField loginTextField;
    private final JTextField passwdTextField;

    public LoginPanel(JFrame frame, ShelterManager manager) {
        {
            JPanel loginPanel = new JPanel();
            loginTextField = new JTextField(25);
            loginPanel.add(new JLabel("Login: "));
            loginPanel.add(loginTextField);
            add(loginPanel);
        }
        {
            JPanel passwdPanel = new JPanel();
            passwdTextField = new JTextField(25);
            passwdPanel.add(new JLabel("Password: "));
            passwdPanel.add(passwdTextField);
            add(passwdPanel);
        }

        {
            JButton loginBtn = new JButton("Login");
            loginBtn.addActionListener(e -> {
                String login = loginTextField.getText();
                String passwd = passwdTextField.getText();

                boolean authenticated = login.equals("admin") && passwd.equals("admin");

                frame.remove(this);
                frame.add(new ShelterPanel(manager, frame, authenticated), BorderLayout.CENTER);
                frame.revalidate();
            });
            add(loginBtn, BorderLayout.SOUTH);
        }
    }
}
