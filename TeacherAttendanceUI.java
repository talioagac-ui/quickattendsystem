import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TeacherAttendanceUI {

    public static void show(String userName, String userRole, int teacherId) {

        JFrame frame = new JFrame("Teacher " + teacherId + " Attendance");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

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

        sidebar.add(createBtn("QR CODE", frame, userName, userRole));
        if (!userRole.equalsIgnoreCase("Student")) {
            sidebar.add(createBtn("ADMIN", frame, userName, userRole));
        }
        sidebar.add(createBtn("TEACHERS", frame, userName, userRole));
        sidebar.add(createBtn("STUDENTS", frame, userName, userRole));
        sidebar.add(createBtn("LOG OUT", frame, userName, userRole));

        frame.add(sidebar, BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());

        JPanel topbar = new JPanel(new BorderLayout());
        topbar.setPreferredSize(new Dimension(0, 80));
        topbar.setBackground(new Color(0, 153, 255));

        JLabel title = new JLabel("   TEACHER " + teacherId + " ATTENDANCE");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

        JLabel dateTime = new JLabel();
        dateTime.setForeground(Color.WHITE);
        dateTime.setFont(new Font("Arial", Font.BOLD, 14));

        new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
            dateTime.setText(sdf.format(new Date()));
        }).start();

        topbar.add(title, BorderLayout.WEST);
        topbar.add(dateTime, BorderLayout.EAST);

        main.add(topbar, BorderLayout.NORTH);

        JPanel contentContainer = new JPanel(new BorderLayout(0, 20));
        contentContainer.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        if (userRole.equalsIgnoreCase("Teacher") || userRole.equalsIgnoreCase("Admin")) {
            String[] columns = {"Month", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

            DefaultTableModel model = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            model.addRow(new Object[]{"May", autoStatus(), autoStatus(), autoStatus(), autoStatus(), autoStatus(), autoStatus(), autoStatus()});
            model.addRow(new Object[]{"June", autoStatus(), autoStatus(), autoStatus(), autoStatus(), autoStatus(), autoStatus(), autoStatus()});

            JTable table = new JTable(model);
            table.setRowHeight(30);
            table.setEnabled(false);

            JScrollPane scrollPane = new JScrollPane(table);
            contentContainer.add(scrollPane, BorderLayout.CENTER);

        } else {
            JPanel privacyPanel = new JPanel(new GridBagLayout());
            JLabel iconLabel = new JLabel("RESTRICTED ACCESS", SwingConstants.CENTER);
            iconLabel.setFont(new Font("Arial", Font.BOLD, 24));
            iconLabel.setForeground(new Color(200, 50, 50));

            JLabel messageLabel = new JLabel("Due to privacy policies, only teachers can view this attendance data.", SwingConstants.CENTER);
            messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            messageLabel.setForeground(Color.DARK_GRAY);

            JPanel messageWrapper = new JPanel(new GridLayout(2, 1, 0, 10));
            messageWrapper.setOpaque(false);
            messageWrapper.add(iconLabel);
            messageWrapper.add(messageLabel);

            privacyPanel.add(messageWrapper);
            contentContainer.add(privacyPanel, BorderLayout.CENTER);
        }

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));

        JButton backBtn = new JButton("← BACK TO TEACHERS");
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(0, 153, 255));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setPreferredSize(new Dimension(220, 40));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backBtn.addActionListener(e -> {
            frame.dispose();
            EnglishTeacherUI.show(userName, userRole);
        });

        bottomPanel.add(backBtn);
        contentContainer.add(bottomPanel, BorderLayout.SOUTH);

        main.add(contentContainer, BorderLayout.CENTER);
        frame.add(main, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    static String autoStatus() {
        double rand = Math.random();
        if (rand < 0.7) return "Present";
        else if (rand < 0.9) return "Late";
        else return "Absent";
    }

    static JButton createBtn(String text, JFrame frame, String userName, String userRole) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(70, 126, 179));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            frame.dispose();

            switch (text) {
                case "ADMIN":
                    AdminUI.show(userName, userRole);
                    break;
                case "TEACHERS":
                    TeacherDepartmentsUI.show(userName, userRole);
                    break;
                case "STUDENTS":
                    StudentUI.show(userName, userRole);
                    break;
                case "QR CODE":
                    SwingUtilities.invokeLater(() -> new ScannerApp().setVisible(true));
                    break;
                default:
                    JOptionPane.showMessageDialog(null, text + " clicked");
            }
        });
        return btn;
    }
}