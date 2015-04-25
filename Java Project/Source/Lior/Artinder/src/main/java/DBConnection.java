//STEP 1. Import required packages
import java.sql.*;

public class DBConnection {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Artinder";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "1234";
    private static Connection connection;


    public static void Connect() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void GetAllArticles() throws SQLException {

    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM Articels");

        while (rs.next()) {
            String lastName = rs.getString("Content");
            System.out.println(lastName + "\n");
        }
    }

    public static void GetAllUsers() throws SQLException {

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {
            String lastName = rs.getString("Content");
            System.out.println(lastName + "\n");
        }
    }

    public static boolean DoesUserExist(String userid) throws SQLException {

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users where userid = " + userid);

        if (rs.next()) {
            return true;
        }

        return false;
    }

}//end JDBCExample