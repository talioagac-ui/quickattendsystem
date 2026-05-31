import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnector {
    
    public static Connection getConnection() {
        Connection connection = null;
        
        String url = "jdbc:mysql://localhost:3306/quickattend_db";
        String user = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Success! Connected to quickattend_db.");
            
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
        
        return connection;
    }

    public static void main(String[] args) {
        getConnection();
    }
}