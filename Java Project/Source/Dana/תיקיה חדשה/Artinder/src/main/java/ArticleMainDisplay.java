
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * Created by Lior on 08/04/2015.
 */
public class ArticleMainDisplay extends JFrame{

    private JPanel panel;
    private JTextField FirstArticle;
    private JTextField SecondArticle;
    private JButton FirstExpand;
    private JButton SecondExpand;

    public ArticleMainDisplay() throws SQLException, ClassNotFoundException {
        super();
        this.setContentPane(panel);
        pack();
        setVisible(true);
        // Show 2 random articles

        List<Article> unClassifiedArticales = DBConnection.GetAllUnclassifiedArticlesForUser(Main.userConnected);
        Random myRandomizer = new Random();

        Article FirstRandomArticle = unClassifiedArticales.get(myRandomizer.nextInt(unClassifiedArticales.size()));
        Article SecondRandomArticle = unClassifiedArticales.get(myRandomizer.nextInt(unClassifiedArticales.size()));

        FirstArticle.setText(FirstRandomArticle.text.toString());
        SecondArticle.setText(SecondRandomArticle.text.toString());

    }

}
