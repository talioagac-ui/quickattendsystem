import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class ScannerApp extends JFrame {

    public ScannerApp() {
        setTitle("Scanner UI");
        setSize(350, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new ScannerUI());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ScannerApp app = new ScannerApp();
            app.setVisible(true);
        });
    }
}

class ScannerUI extends JPanel {

    private int btnSize = 80;
    private int btnX;
    private int btnY;

    public ScannerUI() {
        setBackground(new Color(240, 240, 240));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getX() >= btnX && e.getX() <= btnX + btnSize &&
                    e.getY() >= btnY && e.getY() <= btnY + btnSize) {
                    
                    triggerScanProcess();
                }
            }
        });
    }

    private void triggerScanProcess() {
        String decodedQRCode = "STUD-2026-001";
        
        JOptionPane.showMessageDialog(this, 
            "QR Code Scanned: " + decodedQRCode + "\nLogging to quickattend_db...", 
            "Scan Successful", 
            JOptionPane.INFORMATION_MESSAGE);

        try (Connection conn = dbconnector.getConnection()) {
            if (conn != null) {
                String query = "INSERT INTO attendance (student_id, time_in) VALUES (?, NOW())";
                
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, decodedQRCode);
                    int rowsAffected = stmt.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        System.out.println("Attendance logged successfully for: " + decodedQRCode);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Database connection failed! Ensure XAMPP is running.", 
                    "Connection Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Database error: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
         
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(new Color(170, 170, 170));
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString("Scanner", 30, 50);

        int boxWidth = 220;
        int boxHeight = 220;
        int x = (getWidth() - boxWidth) / 2;
        int y = 120;
        int arcSize = 50;

        g2d.setColor(new Color(155, 155, 155));

        g2d.fillArc(x, y, arcSize, arcSize, 90, 90);
        g2d.fillArc(x + boxWidth - arcSize, y, arcSize, arcSize, 0, 90);
        g2d.fillArc(x, y + boxHeight - arcSize, arcSize, arcSize, 180, 90);
        g2d.fillArc(x + boxWidth - arcSize, y + boxHeight - arcSize, arcSize, arcSize, 270, 90);

        btnX = (getWidth() - btnSize) / 2;
        btnY = y + boxHeight + 80;

        g2d.setColor(new Color(210, 210, 210));
        g2d.fillOval(btnX - 4, btnY - 4, btnSize + 8, btnSize + 8);

        g2d.setColor(new Color(150, 150, 150));
        g2d.fillOval(btnX, btnY, btnSize, btnSize);
    }
}