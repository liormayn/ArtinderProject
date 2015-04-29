//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


    public static boolean RegisterUser(String username,String password) throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed())
        {
            Connect();
        }

        if (!DoesUserExist(username)) {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate(" INSERT INTO users (username,password)\n" +
                    "        VALUES ('" + username + "','" + password + "');");

            if (rs == 1) return true;
        }
        return false;
    }

    public static List<Article> GetAllArticles() throws SQLException, ClassNotFoundException {

        if (connection == null || connection.isClosed())
        {
            Connect();
        }
    List<Article> articles = new ArrayList<>();
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM Articles");

        while (rs.next()) {
            Article a = new Article();
            a.id = rs.getInt("articleid");
            a.text = rs.getString("Content");
            articles.add(a);
        }

        return articles;
    }

    public static List<User> GetAllUsers() throws SQLException, ClassNotFoundException {

        if (connection == null || connection.isClosed())
        {
            Connect();
        }

        List<User> users = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        while (rs.next()) {
            User a = new User();
            a.username = rs.getString("username");
            a.password = rs.getString("password");
            users.add(a);
        }

        return users;
    }

    public static boolean DoesUserExist(String username) throws SQLException, ClassNotFoundException {

        if (connection == null || connection.isClosed())
        {
            Connect();
        }

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users where username = '" + username + "'");

        if (rs.next()) {
            return true;
        }

        return false;
    }

    public static boolean CanUserConnect(String username,String password) throws SQLException, ClassNotFoundException {

        User userToCheck = new User();
        userToCheck.password = password;
        userToCheck.username = username;
        List<User> users = GetAllUsers();
        for (User item : users) {
            if (item.password.equals(password) && item.username.equals(username))
            {
                return true;
            }
        }
        return false;
    }

    public static List<Article> GetUserPositiveClassifications(String username) throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed())
        {
            Connect();
        }

        List<Article> articles = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Articles WHERE username = '" + username + "' and Classification = 1 ");

        while (rs.next()) {
            Article a = new Article();
            a.id = rs.getInt("articleid");
            a.text = rs.getString("Content");
            articles.add(a);
        }

        return articles;
    }

    public static List<Article> GetUserNegativeClassifications(String username) throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed())
        {
            Connect();
        }

        List<Article> articles = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Articles WHERE username '" + username + "' and Classification = 0 ");

        while (rs.next()) {
            Article a = new Article();
            a.id = rs.getInt("articleid");
            a.text = rs.getString("Content");
            articles.add(a);
        }

        return articles;
    }

    public static List<Article> GetAllUnclassifiedArticlesForUser(String username)  throws SQLException, ClassNotFoundException{
        if (connection == null || connection.isClosed())
        {
            Connect();
        }
        List<Article> AllArticles = GetAllArticles();
        List<Article> PositiveArticles = GetUserPositiveClassifications(username);
        List<Article> NegativeArticles = GetUserNegativeClassifications(username);

        List<Article> UnclassifiedArticles = new ArrayList<Article>();

        for (Article item : AllArticles)
        {
            if ((!PositiveArticles.contains(item)) && (!NegativeArticles.contains(item)))
            {
                UnclassifiedArticles.add(item);
            }
        }

        return UnclassifiedArticles;
    }

    public static boolean SetClassification(String username,int articleId, boolean classification) throws SQLException, ClassNotFoundException
    {
        //1 - positive 0-negative
        //userid , articleid ,classification
        int classificationToInsert = 0;
        if (classification) classificationToInsert = 1;

        if (connection == null || connection.isClosed())
        {
            Connect();
        }

        if (IsArticleClassified(username,articleId))
        {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate("UPDATE articleclassification SET classification= " +
                    String.valueOf(classificationToInsert) + " where userid = '" + username + "'" +
                    "AND articleid = '" + String.valueOf(articleId) + "' ");
            if (rs == 1) return true;


        }

        else
        {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate(" INSERT INTO articleclassification (username,articleid,category,classification)" +
                    " VALUES ('" + username + "','" + String.valueOf(articleId) + "' , 'General' , " + String.valueOf(classificationToInsert) + "' );");

            if (rs == 1) return true;
        }

        return false;
    }

    public static boolean IsArticleClassified(String username,int articleId) throws SQLException, ClassNotFoundException
    {
        if (connection == null || connection.isClosed())
        {
            Connect();
        }

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM articleclassification where userid = '" + username + "' AND articleid = '" + String.valueOf(articleId) + "' ");

        if (rs.next()) {
            return true;
        }

        return false;
    }


}//end JDBCExample