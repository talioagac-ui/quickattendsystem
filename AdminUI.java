import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;



public class AdminUI {



    public static void show(String userName, String userRole) {



        JFrame frame = new JFrame("Admin");

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        sidebar.setLayout(new GridLayout(9, 1, 0, 15));



        JLabel avatar = new JLabel("Avatar", SwingConstants.CENTER);

        avatar.setForeground(Color.WHITE);



        JLabel name = new JLabel(userName, SwingConstants.CENTER);

        name.setForeground(Color.WHITE);



        JLabel role = new JLabel(userRole, SwingConstants.CENTER);

        role.setForeground(Color.LIGHT_GRAY);



        sidebar.add(avatar);

        sidebar.add(name);

        sidebar.add(role);



        sidebar.add(createBtn("ADMIN", true, frame, userName, userRole, timer));

        sidebar.add(createBtn("TEACHERS", false, frame, userName, userRole, timer));

        sidebar.add(createBtn("STUDENTS", false, frame, userName, userRole, timer));

        sidebar.add(createBtn("LOG OUT", false, frame, userName, userRole, timer));



        frame.add(sidebar, BorderLayout.WEST);



        JPanel main = new JPanel(new BorderLayout());



        JPanel topbar = new JPanel(new BorderLayout());

        topbar.setPreferredSize(new Dimension(0, 80));

        topbar.setBackground(new Color(0, 153, 255));

        topbar.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));



        JLabel title = new JLabel("SYSTEM ADMINS");

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



        String[] columnNames = {"Admin ID", "Admin Name", "Role / Department"};
       

        Object[][] data = {

            {"1", "Lloyd batumbakal", "ADMIN"},
            {"2", "Mary anne Casle", "ADMIN"},
            {"3", "Jorge De Asis", "ADMIN"},
            {"4", "Flor Mabayan", "ADMIN"},
            {"5", "Kurt Bautista", "ADMIN"},
            {"6", "Shane Bilog", "ADMIN"},
            {"7", "JP Jesus", "ADMIN"},
            {"8", "Marian Lais", "ADMIN"},
            {"9", "Jon Baluyot", "ADMIN"},
            {"10", "Trisha Ginto", "ADMIN"},
            {"11", "Jordan Monton", "ADMIN"}

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
       

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       

        JTableHeader header = table.getTableHeader();

        header.setFont(new Font("Arial", Font.BOLD, 16));

        header.setBackground(new Color(70, 126, 179));

        header.setForeground(Color.WHITE);



        table.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent e) {

                int row = table.getSelectedRow();

                if (row != -1) {

                    timer.stop();

                    String adminId = (String) table.getValueAt(row, 0);

                    String adminName = (String) table.getValueAt(row, 1);
                   

                    JOptionPane.showMessageDialog(null, "Opening profile for: " + adminName + " (ID: " + adminId + ")");

                }

            }

        });



        JScrollPane scrollPane = new JScrollPane(table);
       

        JPanel tableContainer = new JPanel(new BorderLayout(0, 20));

        tableContainer.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        tableContainer.add(scrollPane, BorderLayout.CENTER);



        main.add(tableContainer, BorderLayout.CENTER);

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

}