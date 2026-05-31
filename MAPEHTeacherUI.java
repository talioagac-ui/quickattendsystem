import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class MAPEHTeacherUI {

    public static void show(String userName, String userRole) {

        JFrame frame = new JFrame("MAPEH Teachers");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        sidebar.add(createBtn("QR CODE", false, frame, userName, userRole));
        if (!userRole.equalsIgnoreCase("Student")) {
            sidebar.add(createBtn("ADMIN", false, frame, userName, userRole));
        }
        sidebar.add(createBtn("TEACHERS", true, frame, userName, userRole));
        sidebar.add(createBtn("STUDENTS", false, frame, userName, userRole));
        sidebar.add(createBtn("LOG OUT", false, frame, userName, userRole));
        frame.add(sidebar, BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());

        JPanel topbar = new JPanel(new BorderLayout());
        topbar.setPreferredSize(new Dimension(0, 80));
        topbar.setBackground(new Color(0, 153, 255));
        topbar.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel("MUSIC, ARTS, PHYSICAL EDUCATION, HEALTH DEPARTMENT");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(Color.WHITE);

        JLabel dateTime = new JLabel();
        dateTime.setForeground(Color.WHITE);
        dateTime.setFont(new Font("Arial", Font.BOLD, 14));

        Timer timer = new Timer(1000, e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
            dateTime.setText(sdf.format(new Date()));
        });
        timer.start();

        JPanel right = new JPanel(new BorderLayout());
        right.setOpaque(false);

        dateTime.setHorizontalAlignment(SwingConstants.CENTER);
        dateTime.setVerticalAlignment(SwingConstants.CENTER);

        right.add(dateTime, BorderLayout.CENTER);

        topbar.add(title, BorderLayout.WEST);
        topbar.add(right, BorderLayout.EAST);

        main.add(topbar, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Teacher Name"};
        
        // Dummy data for MAPEH (Music, Arts, PE, Health)
      Object[][] data = {
            {"6", "Liza Ramos"},
            {"12", "Angelica Lopez"},
            {"18", "Clarissa Morales"}
};

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(0, 153, 255));
        table.setSelectionForeground(Color.WHITE);
        table.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBackground(new Color(70, 126, 179));
        header.setForeground(Color.WHITE);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    frame.dispose();
                    int teacherId = Integer.parseInt((String) table.getValueAt(row, 0));
                    TeacherAttendanceUI.show(userName, userRole, teacherId);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        
        JPanel tableContainer = new JPanel(new BorderLayout(0, 20));
        tableContainer.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        tableContainer.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        
        JButton backBtn = new JButton("← BACK TO DEPARTMENTS");
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(0, 153, 255));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setPreferredSize(new Dimension(250, 40));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backBtn.addActionListener(e -> {
            frame.dispose();
            TeacherDepartmentsUI.show(userName, userRole);
        });

        bottomPanel.add(backBtn);
        tableContainer.add(bottomPanel, BorderLayout.SOUTH);

        main.add(tableContainer, BorderLayout.CENTER);

        frame.add(main, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    static JButton createBtn(String text, boolean isActive,
                             JFrame frame,
                             String userName,
                             String userRole) {

        JButton btn = new JButton(text);

        btn.setForeground(Color.WHITE);
        btn.setBackground(
                isActive ? new Color(0, 153, 255) : new Color(70, 126, 179)
        );
        
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
                default:
                    JOptionPane.showMessageDialog(null, text + " clicked");
            }
        });

        return btn;
    }
}