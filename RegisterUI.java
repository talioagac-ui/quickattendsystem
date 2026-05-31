import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterUI {

    public static void show() {
        JFrame frame = new JFrame("Register New User"); 
        frame.setSize(400, 600); 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 153, 255));
        headerPanel.setPreferredSize(new Dimension(400, 60));
        JLabel titleLabel = new JLabel("USER REGISTRATION"); 
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        frame.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField nameField = new JTextField(15);
        formPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        JTextField emailField = new JTextField(15);
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        JPasswordField passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        JTextField ageField = new JTextField(15);
        formPanel.add(ageField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderBox = new JComboBox<>(genders);
        formPanel.add(genderBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        String[] roles = {"Student", "Teacher", "Admin"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        formPanel.add(roleBox, gbc);

        frame.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton registerBtn = new JButton("Register");
        registerBtn.setBackground(new Color(0, 153, 255));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFocusPainted(false);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFocusPainted(false);

        buttonPanel.add(registerBtn);
        buttonPanel.add(cancelBtn);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        
        cancelBtn.addActionListener(e -> frame.dispose());

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText(); 
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String ageStr = ageField.getText();
                String gender = (String) genderBox.getSelectedItem();
                String role = (String) roleBox.getSelectedItem(); 

                if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || ageStr.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int age;
                try {
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Age must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Connection conn = dbconnector.getConnection();
                    if (conn != null) {
                        String query = "INSERT INTO users (email, password, full_name, username, gender, age, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement pst = conn.prepareStatement(query);

                        pst.setString(1, email);
                        pst.setString(2, password);
                        pst.setString(3, name);
                        pst.setString(4, username); 
                        pst.setString(5, gender);
                        pst.setInt(6, age);
                        pst.setString(7, role);

                        int rowsInserted = pst.executeUpdate();
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(frame, "Registration Successful as " + role + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            frame.dispose();
                        }
                        conn.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }
}