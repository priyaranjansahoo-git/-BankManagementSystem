import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1"; // Update as per your configuration
    private static final String DB_USER = "SYSTEM";
    private static final String DB_PASS = "silu";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }
}
