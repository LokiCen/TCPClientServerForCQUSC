import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class test {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/atmserver";
    private static final String USER = "root";
    private static final String PASS = "111111xiaomc";

    public static void main(String[] args) {
        try {
            // 加载数据库驱动（Java 6及以上版本自动加载驱动，该步骤可省略）
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database...");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 如果没有抛出异常，说明连接成功
            System.out.println("Database connected successfully!");

            // 关闭数据库连接
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}
