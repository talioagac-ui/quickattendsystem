import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class TeacherDepartmentsUI {

    public static void show(String userName, String userRole) {

        JFrame frame = new JFrame("Teacher Departments");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        Timer timer = new Timer(1000, e -> {});
        timer.start();

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(150, 0));
        sidebar.setBackground(new Color(70, 126, 179));
        sidebar.setLayout(new GridLayout(10, 1, 0, 15));

        JLabel avatar = new JLabel("Avatar", SwingConstants.CENTER);
        avatar.setForeground(Color.WHITE);

        JLabel name = new JLabel(userName, SwingConstants.CENTER);
        name.setForeground(Color.WHITE);

        JLabel role = new JLabel(userRole, SwingConstants.CENTER);
        role.setForeground(Color.LIGHT_GRAY);

        sidebar.add(avatar);
        sidebar.add(name);
        sidebar.add(role);

        // Sidebar navigation items (ADMIN button removed)
        sidebar.add(createBtn("QR CODE", false, frame, userName, userRole, timer));
        sidebar.add(createBtn("TEACHERS", true, frame, userName, userRole, timer));
        sidebar.add(createBtn("STUDENTS", false, frame, userName, userRole, timer));
        sidebar.add(createBtn("LOG OUT", false, frame, userName, userRole, timer));

        frame.add(sidebar, BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());

        JPanel topbar = new JPanel(new BorderLayout());
        topbar.setPreferredSize(new Dimension(0, 80));
        topbar.setBackground(new Color(0, 153, 255));

        JLabel title = new JLabel("    DEPARTMENTS");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

        JLabel dateTime = new JLabel();
        dateTime.setForeground(Color.WHITE);
        dateTime.setFont(new Font("Arial", Font.BOLD, 14));

        timer.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
            dateTime.setText(sdf.format(new Date()));
        });

        JPanel right = new JPanel(new BorderLayout());
        right.setOpaque(false);

        dateTime.setHorizontalAlignment(SwingConstants.CENTER);
        dateTime.setVerticalAlignment(SwingConstants.CENTER);

        right.add(dateTime, BorderLayout.CENTER);

        topbar.add(title, BorderLayout.WEST);
        topbar.add(right, BorderLayout.EAST);

        main.add(topbar, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(5, 3, 30, 30));
        grid.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        grid.add(createCard("ENGLISH", frame, userName, userRole, timer));
        grid.add(createCard("FILIPINO", frame, userName, userRole, timer));
        grid.add(createCard("MATHEMATICS", frame, userName, userRole, timer));
        grid.add(createCard("ESP", frame, userName, userRole, timer));
        grid.add(createCard("SCIENCE", frame, userName, userRole, timer));
        grid.add(createCard("MAPEH", frame, userName, userRole, timer));

        main.add(grid, BorderLayout.CENTER);

        frame.add(main, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    static JButton createBtn(String text, boolean isActive, JFrame frame, String userName, String userRole, Timer timer) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(isActive ? new Color(0, 153, 255) : new Color(70, 126, 179));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            switch (text) {
                case "ADMIN":
                    if (timer != null) timer.stop();
                    frame.dispose();
                    AdminUI.show(userName, userRole);
                    break;
                case "TEACHERS":
                    if (timer != null) timer.stop();
                    frame.dispose();
                    TeacherDepartmentsUI.show(userName, userRole);
                    break;
                case "STUDENTS":
                    if (timer != null) timer.stop();
                    frame.dispose();
                    StudentUI.show(userName, userRole);
                    break;
                case "QR CODE":
                    SwingUtilities.invokeLater(() -> {
                        ScannerApp scanner = new ScannerApp();
                        scanner.setVisible(true);
                    });
                    break;
                case "LOG OUT":
                    if (timer != null) timer.stop();
                    frame.dispose();
                    JOptionPane.showMessageDialog(null, "Logged Out");
                    QuickAttendUI.show();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, text + " clicked");
            }
        });
        return btn;
    }

    static JPanel createCard(String text, JFrame frame, String userName, String userRole, Timer timer) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(70, 126, 179));
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setForeground(Color.WHITE);
        card.add(label, BorderLayout.CENTER);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (timer != null) timer.stop();
                frame.dispose();
                switch (text) {
                    case "ENGLISH": EnglishTeacherUI.show(userName, userRole); break;
                    case "FILIPINO": FilipinoTeacherUI.show(userName, userRole); break;
                    case "MATHEMATICS": MathTeacherUI.show(userName, userRole); break;
                    case "SCIENCE": ScienceTeacherUI.show(userName, userRole); break;
                    case "ESP": ESPTeacherUI.show(userName, userRole); break;
                    case "MAPEH": MAPEHTeacherUI.show(userName, userRole); break;
                    default: JOptionPane.showMessageDialog(null, "No page yet for " + text);
                }
            }
        });
        return card;
    }
}