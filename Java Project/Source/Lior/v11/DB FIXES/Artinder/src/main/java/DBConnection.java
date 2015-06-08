//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DBConnection {
    // JDBC driver name and database URL

    //local DB!
    static final String DB_URL = "jdbc:mysql://localhost/Artinder";
    static final String USER = "root";
    static final String PASS = "1234";

/* //ColmanDB
    static final String DB_URL = "jdbc:mysql://com3.cs.colman.ac.il/Artinder";
    static final String USER = "Artinder";
    static final String PASS = "art@inder";
    */

    //static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static Connection connection;
    //private static int validThreshold = 1;

    private static List<Article> allArticles;


    public static void Connect() throws SQLException, ClassNotFoundException {
        //if (connection == null || !connection.isValid(validThreshold)) return;

        Class.forName("com.mysql.jdbc.Driver");

        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        CloseConnection();
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Connected...");
    }

    public static void CloseConnection() throws SQLException, ClassNotFoundException {
        if ((connection != null) && (!connection.isClosed()))
        {

            connection.close();
            System.out.println("Connection closed...");
        }
    }


    public static boolean RegisterUser(String username,String password) throws SQLException, ClassNotFoundException {
        //if (connection == null || !connection.isValid(validThreshold))
       // {
            Connect();
      // }

        if (!DoesUserExist(username)) {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate(" INSERT INTO users (username,password)\n" +
                    "        VALUES ('" + username + "','" + password + "');");

            if (rs == 1) return true;
        }
        return false;
    }

    public static List<Article> GetAllArticles() throws SQLException, ClassNotFoundException {


        int numOfArticlesPerQuery = 29;
        int startIndex = 0;
        int endIndex = numOfArticlesPerQuery;
        //boolean ra = connection.isValid(validThreshold);
        //if (connection == null || ! ra)
       // {
            Connect();
       // }
        allArticles = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM articles LIMIT 0, 300");// + startIndex + " , " + endIndex);

        while (rs.next()) {
            Article a = new Article();
            a.id = rs.getInt("articleid");
            a.text = rs.getString("Content");
            allArticles.add(a);

            //LIOR
            if (a.text.length() < 250 ) System.out.println("id " + a.id);
        }

        return allArticles;
    }

    public static List<User> GetAllUsers() throws SQLException, ClassNotFoundException {

       // if (connection == null || !connection.isValid(validThreshold))
       // {
            Connect();
      //  }

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

        //if (connection == null || !connection.isValid(validThreshold))
        //{
            Connect();
        //}

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users where username = '" + username + "'");

        if (rs.next()) {
            return true;
        }

        return false;
    }

    public static boolean CanUserConnect(String username,String password) throws SQLException, ClassNotFoundException {

        Connect();
        User userToCheck = new User();
        userToCheck.password = password;
        userToCheck.username = username;

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users where username='" + username + "' and password='" + password + "'");

        if (rs.next()) {
            return true;
        }

        return false;
    }

    private static List<Article> GetUserPositiveClassifications(String username) throws SQLException, ClassNotFoundException {
       // if (connection == null || !connection.isValid(validThreshold))
        //{
            Connect();
       // }
        if (allArticles == null ) GetAllArticles();
       // List<Article> allArticles = GetAllArticles();
        List<Article> articles = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM articleclassification WHERE username = '" + username + "' and Classification = 1 ");

        while (rs.next()) {
            for (Article item : allArticles) {
                if (item.id == rs.getInt("articleid"))
                {
                    articles.add(item);
                }
            }
        }

        return articles;
    }

    private static List<Article> GetUserNegativeClassifications(String username) throws SQLException, ClassNotFoundException {
        //if (connection == null || !connection.isValid(validThreshold))
        //{
            Connect();
        //}
        if (allArticles == null ) GetAllArticles();
        List<Article> articles = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM articleclassification WHERE username = '" + username + "' and Classification = 0 ");

        while (rs.next()) {
            for (Article item : allArticles) {
                if (item.id == rs.getInt("articleid"))
                {
                    articles.add(item);
                }
            }
        }

        return articles;
    }

    public static List<Article> GetAllUnclassifiedArticlesForUser(String username)  throws SQLException, ClassNotFoundException{
        //if (connection == null || !connection.isValid(validThreshold))
        //{
           // Connect();
        //}
        if (allArticles == null) GetAllArticles();
        List<Article> PositiveArticles = GetUserPositiveClassifications(username);
        List<Article> NegativeArticles = GetUserNegativeClassifications(username);
        ArticleFinder.Positive = PositiveArticles;
        ArticleFinder.Negative = NegativeArticles;

        List<Article> UnclassifiedArticles = allArticles.stream().filter(item -> (!PositiveArticles.contains(item)) && (!NegativeArticles.contains(item))).collect(Collectors.toList());

        ArticleFinder.Unclassified = UnclassifiedArticles;
        return UnclassifiedArticles;
    }

    public static int GetPositiveClassificationCount(String username) throws SQLException, ClassNotFoundException{
    {
        Connect();
        List<Article> articles = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM articleclassification WHERE username = '" + username + "' and Classification = 1 ");

        while (rs.next()) {
                return rs.getInt("count(*)");
            }
        }

        return 0;
    }


    public static int GetNegativeClassificationCount(String username) throws SQLException, ClassNotFoundException{
        {
            Connect();
            List<Article> articles = new ArrayList<>();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM articleclassification WHERE username = '" + username + "' and Classification = 0 ");

            while (rs.next()) {
                return rs.getInt("count(*)");
            }
        }

        return 0;
    }

    public static boolean SetClassification(String username,int articleId, boolean classification) throws SQLException, ClassNotFoundException
    {
        //1 - positive 0-negative
        //userid , articleid ,classification
        int classificationToInsert = 0;
        if (classification) classificationToInsert = 1;

        //if (connection == null || !connection.isValid(validThreshold))
        //{
            Connect();
        //}

        if (IsArticleClassified(username,articleId))
        {
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate("UPDATE articleclassification SET classification= " +
                    String.valueOf(classificationToInsert) + " where username = '" + username + "'" +
                    "AND articleid = '" + String.valueOf(articleId) + "' ");
            if (rs == 1) return true;


        }

        else
        {
            Statement stmt = connection.createStatement();

            // TODO: check if the category in articleclassification in the db should be number or string, for now put 1 as general
            int rs = stmt.executeUpdate(" INSERT INTO articleclassification (articleid,username,category,classification)" +
                    " VALUES ('" + String.valueOf(articleId) + "','" + username + "' , '1' , '" + String.valueOf(classificationToInsert) + "' );");

            if (rs == 1) return true;
        }

        return false;
    }

    public static boolean IsArticleClassified(String username,int articleId) throws SQLException, ClassNotFoundException
    {
       // if (connection == null || !connection.isValid(validThreshold)) {
            Connect();
       // }

        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM articleclassification where username = '" + username + "' AND articleid = '" + String.valueOf(articleId) + "' ");

        if (rs.next()) {
            return true;
        }

        return false;
    }


}//end JDBCExample