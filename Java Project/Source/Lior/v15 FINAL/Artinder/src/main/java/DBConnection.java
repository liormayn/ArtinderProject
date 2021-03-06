//STEP 1. Import required packages
import sun.plugin2.main.client.DisconnectedExecutionContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DBConnection {
    // JDBC driver name and database URL

    /*
    //local DB!
    static final String DB_URL = "jdbc:mysql://localhost/Artinder";
    static final String USER = "root";
    static final String PASS = "1234";
*/
//ColmanDB
    static final String DB_URL = "jdbc:mysql://com3.cs.colman.ac.il/Artinder";
    static final String SQLiteConnection = "jdbc:sqlite:artinder_data.sqlite";
    static final String USER = "Artinder";
    static final String PASS = "art@inder";

    private static boolean localConnection = true;
    private static Connection connection;

    private static List<Article> allArticles;

    public static void SetConnectionType(boolean local)
    {
        localConnection = local;
    }
    public static boolean IsConnectionLocal()
    {
        return localConnection;
    }

    public static void Connect() throws SQLException, ClassNotFoundException {
        if (connection != null && connection.isValid(5)) return;

        System.out.println("Connecting to database...");

        // SQLLite for local connections
        if (localConnection) {
            System.out.println("LOCAL...");
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(SQLiteConnection);
        }
        else
        {
            System.out.println("REMOTE...");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }

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

        if (!DoesUserExist(username)) {
            try {
                Connect();
                Statement stmt = connection.createStatement();
                int rs = stmt.executeUpdate(" INSERT INTO users (username,password)\n" +
                        "        VALUES ('" + username + "','" + password + "');");

                if (rs == 1) return true;
            } finally {
                CloseConnection();
            }
        }

        return false;
    }

    public static List<Article> GetAllArticles() throws SQLException, ClassNotFoundException {
        allArticles = new ArrayList<>();
        try {
            Connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM articles");

            while (rs.next()) {
                Article a = new Article();
                a.id = rs.getInt("articleid");
                a.text = rs.getString("Content");
                allArticles.add(a);

                //LIOR
                if (a.text.length() < 250) System.out.println("id " + a.id);
            }

            return allArticles;
        }
        finally {
            CloseConnection();
        }
    }

    public static List<User> GetAllUsers() throws SQLException, ClassNotFoundException {

        List<User> users = new ArrayList<>();

        try {
            Connect();
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
        finally {
            CloseConnection();
        }
    }

    public static boolean DoesUserExist(String username) throws SQLException, ClassNotFoundException {

        //if (connection == null || !connection.isValid(validThreshold))
        //{
        //}


        try {
            Connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users where username = '" + username + "'");

            if (rs.next()) {
                return true;
            }

            return false;
        }
        finally {
            CloseConnection();
        }
    }

    public static boolean CanUserConnect(String username,String password) throws SQLException, ClassNotFoundException {
        User userToCheck = new User();
        userToCheck.password = password;
        userToCheck.username = username;

        try {
            Connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users where username='" + username + "' and password='" + password + "'");

            if (rs.next()) {
                return true;
            }
        } finally {
            CloseConnection();
        }

        return false;
    }

    private static List<Article> GetUserPositiveClassifications(String username) throws SQLException, ClassNotFoundException {

        if (allArticles == null) GetAllArticles();
        List<Article> articles = new ArrayList<>();

        try {
            Connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM articleclassification WHERE username = '" + username + "' and Classification = 1 ");

            while (rs.next()) {
                for (Article item : allArticles) {
                    if (item.id == rs.getInt("articleid")) {
                        articles.add(item);
                    }
                }
            }
        } finally {
            CloseConnection();
        }
        return articles;
    }

    private static List<Article> GetUserNegativeClassifications(String username) throws SQLException, ClassNotFoundException {
        if (allArticles == null) GetAllArticles();
        List<Article> articles = new ArrayList<>();

        try {
            Connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM articleclassification WHERE username = '" + username + "' and Classification = 0 ");

            while (rs.next()) {
                for (Article item : allArticles) {
                    if (item.id == rs.getInt("articleid")) {
                        articles.add(item);
                    }
                }
            }
        } finally {
            CloseConnection();
        }

        return articles;
    }

    public static List<Article> GetAllUnclassifiedArticlesForUser(String username)  throws SQLException, ClassNotFoundException{
        if (allArticles == null) GetAllArticles();
        List<Article> PositiveArticles = GetUserPositiveClassifications(username);
        List<Article> NegativeArticles = GetUserNegativeClassifications(username);
        ArticleFinder.Positive = PositiveArticles;
        ArticleFinder.Negative = NegativeArticles;

        List<Article> UnclassifiedArticles = allArticles.stream().filter(item -> (!PositiveArticles.contains(item)) && (!NegativeArticles.contains(item))).collect(Collectors.toList());

        ArticleFinder.Unclassified = UnclassifiedArticles;
        return UnclassifiedArticles;
    }

    public static int GetPositiveClassificationCount(String username) throws SQLException, ClassNotFoundException {
        List<Article> articles = new ArrayList<>();
        try {
            Connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM articleclassification WHERE username = '" + username + "' and Classification = 1 ");

            while (rs.next()) {
                System.out.println("user positive count: " + rs.getInt("count(*)"));
                return rs.getInt("count(*)");
            }
        } finally {
            CloseConnection();
        }
        return 0;

    }


    public static int GetNegativeClassificationCount(String username) throws SQLException, ClassNotFoundException {
        List<Article> articles = new ArrayList<>();
        try {
            Connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT count(*) FROM articleclassification WHERE username = '" + username + "' and Classification = 0 ");

            while (rs.next()) {
                System.out.println("user Negative count: " + rs.getInt("count(*)"));
                return rs.getInt("count(*)");
            }
        } finally {
            CloseConnection();
        }
        return 0;

    }

    public static boolean SetClassification(String username,int articleId, boolean classification) throws SQLException, ClassNotFoundException
    {
        int classificationToInsert = 0;
        if (classification) classificationToInsert = 1;

        try {
            Connect();
            Statement stmt = connection.createStatement();

            // TODO: check if the category in articleclassification in the db should be number or string, for now put 1 as general
            int rs = stmt.executeUpdate(" REPLACE INTO articleclassification (articleid,username,category,classification)" +
                    " VALUES ('" + String.valueOf(articleId) + "','" + username + "' , '1' , '" + String.valueOf(classificationToInsert) + "' );");

            if (rs == 1) return true;
        }
        finally {
            CloseConnection();
        }

        return false;
    }

    public static boolean IsArticleClassified(String username,int articleId) throws SQLException, ClassNotFoundException
    {
        try {
            Connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM articleclassification where username = '" + username + "' AND articleid = '" + String.valueOf(articleId) + "' ");

            if (rs.next()) {
                return true;
            }
        }
        finally {
            CloseConnection();
        }

        return false;
    }


}