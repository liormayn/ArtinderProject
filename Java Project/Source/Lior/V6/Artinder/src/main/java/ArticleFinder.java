import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lior on 25/04/2015.
 */
public class ArticleFinder {

    public static ArrayList<Article> GetMatchingArticles(String username) throws Exception {
        List<Article> Unclassified = DBConnection.GetAllUnclassifiedArticlesForUser(username);
        List<Article> Positive = DBConnection.GetUserPositiveClassifications(username);
        List<Article> Negative = DBConnection.GetUserNegativeClassifications(username);

        //Create folder for classified
        //Create folder inside for negative
        //insert negative articles each in text document - title is id and inside - content.

        //Create folder inside for positive
        //insert positive articles each in text document - title is id and inside- content.

        //Create different folder for unclassified articles
        //Create inside folder - "ToClassify"
        //insert each unclassified article in text document - title is id and inside - content.

        //StartClassification algorithm with folders
        //Get a list of id's that are classified as positive.

        String[] arr = new String[] {"C:\\Temp\\FinalProject\\artinder","C:\\Temp\\FinalProject\\Classify"};
        TextClassifier txt = new TextClassifier();
        txt.main(arr);

        // if list is empty / contains less than 2 , add one or two of the unclassified articles to the list.
        //write to console - unclassified added.

        //delete temporary folders.

        return null;

    }
}
