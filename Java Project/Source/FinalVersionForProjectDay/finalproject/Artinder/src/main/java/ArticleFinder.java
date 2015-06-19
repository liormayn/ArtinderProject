import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Lior on 25/04/2015.
 */
public class ArticleFinder {

    private final static String MainFolder = "c:/temp/Artinder";
    public static List<Article> Unclassified;
    public static List<Article>  Positive;
    public static List<Article> Negative;
    private static int processDoneCounter;

    public static List<Article> GetMatchingArticles(String username) throws Exception,FileNotFoundException {
       if (Unclassified == null || Positive == null || Negative == null || Main.firstUseAfterLogin)
       {DBConnection.GetAllUnclassifiedArticlesForUser(username);
       Main.firstUseAfterLogin = false;}
        processDoneCounter = 0;


        //Create the main folder for classified
        File dir = new File(MainFolder + "/classified");
         dir.mkdirs();

        //Runnable NegativeTask = () -> WriteNegative();

        //Runnable PositiveTask =() -> WritePositive();
        //Runnable UnclassifiedTask = () -> WriteUnclassified();

       // Callable<Void> callable = new Callable<Void>() {
          //  public Void call() {
         //       // do something
              //  return null;
         //   }
        //Thread WriteNegativeThread = new Thread(NegativeTask);
        //Thread WritePositiveThread = new Thread(PositiveTask);
       // Thread WriteUnclassifiedThread = new Thread(UnclassifiedTask);

       // WriteNegativeThread.start();
       // WritePositiveThread.start();
       // WriteUnclassifiedThread.start();
        //Create folder inside for negative
        //insert negative articles each in text document - title is id and inside - content.


        dir = new File(MainFolder + "/classified/Negative");
        dir.mkdirs();
        for (Article article : Negative)
        {
            PrintWriter writer = new PrintWriter(MainFolder + "/classified/Negative/"+ article.id);
            writer.println(article.id);
            writer.println(article.text);
            writer.close();
        }

        //Create folder inside for positive
        //insert positive articles each in text document - title is id and inside- content.


        dir = new File(MainFolder + "/classified/Positive");
        dir.mkdirs();
        for (Article article : Positive)
        {
            PrintWriter writer = new PrintWriter(MainFolder + "/classified/Positive/"+ article.id + ".txt");
            writer.println(article.id);
            writer.println(article.text);
            writer.close();
        }


        //Create different folder for unclassified articles
        //Create inside folder - "ToClassify"
        //insert each unclassified article in text document - title is id and inside - content.


        dir = new File(MainFolder  + "/classify/unclassified");
        dir.mkdirs();


        for (Article article : Unclassified)
        {
            PrintWriter writer = new PrintWriter(MainFolder  + "/classify/unclassified/"+ article.id);
            writer.println(article.id);
            writer.println(article.text);
            writer.close();
        }



        //StartClassification algorithm with folders
        //Get a list of id's that are classified as positive.
        String[] arr = new String[] {MainFolder  + "/classified",MainFolder  + "/classify"};
        TextClassifier txt = new TextClassifier();
        List<Integer> articleIdsFound = txt.main(arr);

        // get the chosen articles
            List<Article> chosenArticles = new ArrayList<Article>();

            for ( Article item : Unclassified)
            {
                if (articleIdsFound.contains(item.id))
                {
                    chosenArticles.add(item);
                }
            }


        // if list is empty / contains less than 2 , add one or two of the unclassified articles to the list.
        int randomArticleIndex = 0;
        while  (chosenArticles.size() < 2)
        {
            chosenArticles.add(Unclassified.get(randomArticleIndex));
            randomArticleIndex++;
            System.out.println("Negative classified added because there were not enough positive");
        }

        //delete temporary folders.
        deleteDirectory(new File(MainFolder));

        return chosenArticles;

    }

    static public boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }

}
