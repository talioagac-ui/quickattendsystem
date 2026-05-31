import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class StudentUI {

    public static void show(String userName, String userRole) {

        JFrame frame = new JFrame("Students");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel dateTime = new JLabel();
        dateTime.setForeground(Color.WHITE);
        dateTime.setFont(new Font("Arial", Font.BOLD, 14));

        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
            dateTime.setText(sdf.format(new Date()));
        });
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

        // ✅ Admin sees: ADMIN, QR CODE, TEACHERS, STUDENTS, LOG OUT
        // ✅ Teacher sees: QR CODE, TEACHERS, STUDENTS, LOG OUT
        // ✅ Student sees: QR CODE, STUDENTS, LOG OUT
        if (userRole.equalsIgnoreCase("Admin")) {
            sidebar.add(createBtn("ADMIN", false, frame, userName, userRole, timer));
        }
        sidebar.add(createBtn("QR CODE", false, frame, userName, userRole, timer));
        if (!userRole.equalsIgnoreCase("Student")) {
            sidebar.add(createBtn("TEACHERS", false, frame, userName, userRole, timer));
        }
        sidebar.add(createBtn("STUDENTS", true, frame, userName, userRole, timer));
        sidebar.add(createBtn("LOG OUT", false, frame, userName, userRole, timer));

        frame.add(sidebar, BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());

        JPanel topbar = new JPanel(new BorderLayout());
        topbar.setPreferredSize(new Dimension(0, 80));
        topbar.setBackground(new Color(0, 153, 255));

        JLabel title = new JLabel("   GRADE LEVEL");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

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

        grid.add(createCard("GRADE 1-A", frame, userName, userRole, timer));
        grid.add(createCard("GRADE 1-B", frame, userName, userRole, timer));
        grid.add(createCard("GRADE 2-A", frame, userName, userRole, timer));
        grid.add(createCard("GRADE 2-B", frame, userName, userRole, timer));
        grid.add(createCard("GRADE 3-A", frame, userName, userRole, timer));
        grid.add(createCard("GRADE 3-B", frame, userName, userRole, timer));

        main.add(grid, BorderLayout.CENTER);

        frame.add(main, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    static JButton createBtn(String text, boolean isActive,
                             JFrame frame,
                             String userName,
                             String userRole,
                             Timer timer) {

        JButton btn = new JButton(text);

        btn.setForeground(Color.WHITE);
        btn.setBackground(
                isActive ? new Color(0, 153, 255) : new Color(70, 126, 179)
        );

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

    static JPanel createCard(String text,
                             JFrame frame,
                             String userName,
                             String userRole,
                             Timer timer) {

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
                    case "GRADE 1-A": Grade1AUI.show(userName, userRole); break;
                    case "GRADE 1-B": Grade1BUI.show(userName, userRole); break;
                    case "GRADE 2-A": Grade2AUI.show(userName, userRole); break;
                    case "GRADE 2-B": Grade2BUI.show(userName, userRole); break;
                    case "GRADE 3-A": Grade3AUI.show(userName, userRole); break;
                    case "GRADE 3-B": Grade3BUI.show(userName, userRole); break;
                    default: JOptionPane.showMessageDialog(null, "No page yet for " + text);
                }
            }
        });

        return card;
    }
}