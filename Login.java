import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userText = new JTextField();
        JPasswordField passText = new JPasswordField();
        JButton loginButton = new JButton("Login");

        userLabel.setBounds(50, 50, 100, 30);
        passLabel.setBounds(50, 100, 100, 30);
        userText.setBounds(150, 50, 150, 30);
        passText.setBounds(150, 100, 150, 30);
        loginButton.setBounds(150, 150, 150, 30);

        frame.add(userLabel);
        frame.add(passLabel);
        frame.add(userText);
        frame.add(passText);
        frame.add(loginButton);

        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = userText.getText();
                String pass = new String(passText.getPassword());
                if (validateLogin(user, pass)) {
                    JOptionPane.showMessageDialog(frame, "Login Successful!");
                    // Redirect to the main examination page
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
                }
            }
        });
    }

    private static boolean validateLogin(String username, String password) {
        // Database validation logic here
        // For simplicity, using hardcoded values
        return username.equals("admin") && password.equals("password");
    }
}
