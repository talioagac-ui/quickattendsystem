import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.Border;

public class QuickAttendUI {

    static void setActive(JButton active, JButton... buttons) {
        for (JButton btn : buttons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(70, 126, 179));
            btn.setBorder(BorderFactory.createLineBorder(new Color(70, 126, 179), 1));
        }
        active.setBackground(new Color(80, 198, 255));
        active.setForeground(Color.WHITE);
        active.setBorder(BorderFactory.createLineBorder(new Color(80, 198, 255), 2));
    }

    public static void show() {
        SwingUtilities.invokeLater(() -> QuickAttendUI.main(new String[0]));
    }

    public static void main(String[] args) {
        String[] selectedRole = {""};

        JFrame frame = new JFrame("Quick Attend");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  
        frame.setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        top.setBackground(new Color(70, 126, 179));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(70, 126, 179));

        JLabel openText = new JLabel("OPEN YOUR");
        openText.setForeground(Color.WHITE);
        openText.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel quickAttend = new JLabel(
                "<html><span style='color:white;'>Quick </span>"
                + "<span style='color:#C4E538;'>Attend</span></html>"
        );
        quickAttend.setFont(new Font("Arial", Font.BOLD, 40));

        textPanel.add(openText);
        textPanel.add(quickAttend);
        top.add(textPanel);
        frame.add(top, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(new Color(70, 126, 179));
        frame.add(content, BorderLayout.CENTER);

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(20, 50, 20, 50);
        mainGbc.gridy = 0;

        ImageIcon icon = new ImageIcon("Students.png");
        Image img = icon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        JLabel image = new JLabel(new ImageIcon(img));
        image.setPreferredSize(new Dimension(400, 400));

        mainGbc.gridx = 0;
        content.add(image, mainGbc);

        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(380, 420));
        card.setBackground(Color.WHITE);
        
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        JLabel welcome = new JLabel("Welcome!", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 28));
        welcome.setForeground(new Color(50, 50, 50));
        gbc.gridy = 0;
        card.add(welcome, gbc);

        JPanel rolePanel = new JPanel(new GridLayout(1, 3, 10, 0));
        rolePanel.setOpaque(false);
        JButton adminBtn = new JButton("Admin");
        JButton teacherBtn = new JButton("Teacher");
        JButton studentBtn = new JButton("Student");

        JButton[] roleButtons = {adminBtn, teacherBtn, studentBtn};
        for (JButton btn : roleButtons) {
            btn.setBackground(Color.WHITE);
            btn.setForeground(new Color(70, 126, 179));
            btn.setFocusPainted(false);
            btn.setFont(new Font("Arial", Font.BOLD, 13));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createLineBorder(new Color(70, 126, 179), 1));
            btn.setPreferredSize(new Dimension(0, 35));
            rolePanel.add(btn);
        }
        gbc.gridy = 1;
        card.add(rolePanel, gbc);

        Border fieldPadding = BorderFactory.createEmptyBorder(5, 10, 5, 10);
        
        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(0, 45));
        idField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Email"), fieldPadding));
        gbc.gridy = 2;
        card.add(idField, gbc);

        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(0, 45));
        passField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Password"), fieldPadding));
        gbc.gridy = 3;
        card.add(passField, gbc);

        JButton loginBtn = new JButton("Log In");
        loginBtn.setPreferredSize(new Dimension(0, 40));
        loginBtn.setBackground(new Color(70, 126, 179));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 15));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 40, 5, 40);
        card.add(loginBtn, gbc);

        JButton openRegisterBtn = new JButton("Create an Account");
        openRegisterBtn.setBorderPainted(false);
        openRegisterBtn.setContentAreaFilled(false);
        openRegisterBtn.setFocusPainted(false);
        openRegisterBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openRegisterBtn.setForeground(new Color(70, 126, 179));
        openRegisterBtn.setFont(new Font("Arial", Font.ITALIC, 13));
        
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        card.add(openRegisterBtn, gbc);

        mainGbc.gridx = 1;
        content.add(card, mainGbc);

        adminBtn.addActionListener(e -> {
            selectedRole[0] = "Admin";
            idField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Admin Email"), fieldPadding));
            setActive(adminBtn, roleButtons);
        });

        teacherBtn.addActionListener(e -> {
            selectedRole[0] = "Teacher";
            idField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Teacher Email"), fieldPadding));
            setActive(teacherBtn, roleButtons);
        });

        studentBtn.addActionListener(e -> {
            selectedRole[0] = "Student";
            idField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createTitledBorder("Student Email"), fieldPadding));
            setActive(studentBtn, roleButtons);
        });

        loginBtn.addActionListener(e -> {
            String email = idField.getText().trim();
            String password = new String(passField.getPassword()).trim();

            if (selectedRole[0].isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please SELECT YOUR ROLE!", "Login Error", JOptionPane.WARNING_MESSAGE);
            } else if (email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Email field cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else if (password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Password field cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Connection conn = dbconnector.getConnection();
                    
                    String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND role = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, email);
                    pstmt.setString(2, password);
                    pstmt.setString(3, selectedRole[0]);
                    
                    ResultSet rs = pstmt.executeQuery();
                    
                    if (rs.next()) {
                        JOptionPane.showMessageDialog(frame, "Login Successful! Welcome, " + rs.getString("full_name") + ".");
                        frame.dispose();
                        
                        if (selectedRole[0].equals("Teacher")) {
                            TeacherDepartmentsUI.show(email, "Teacher");
                        } else if (selectedRole[0].equals("Admin")) {
                            AdminUI.show(email, "Admin");
                        } else if (selectedRole[0].equals("Student")) {
                            StudentUI.show(email, "Student");
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Email, Password, or Role combination.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    rs.close();
                    pstmt.close();
                    conn.close();
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Database Connection Error!", "System Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        openRegisterBtn.addActionListener(e -> {
            frame.dispose();
            RegisterUI.show();
        });

        frame.setVisible(true);
    }
}