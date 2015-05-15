
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;


/**
 * Created by Lior on 08/04/2015.
 */
public class ArticleMainDisplay extends JFrame{

    private JPanel panel;
    private JTextArea FirstArticle;
    private JTextArea SecondArticle;
    private JButton FirstExpand;
    private JButton SecondExpand;
    private JButton ReLoad;

    public ArticleMainDisplay() throws Exception {
        super();
        panel = new JPanel();
        this.setContentPane(panel);
        this.setTitle("Artinder");
        //setVisible(true);
        // Show 2 random articles

        List<Article> articlesToChooseFrom = DBConnection.GetAllUnclassifiedArticlesForUser(Main.userConnected);

        if ((DBConnection.GetUserNegativeClassifications(Main.userConnected).size() > Main.ClassificationThreshold)
                || (DBConnection.GetUserPositiveClassifications(Main.userConnected).size() >  Main.ClassificationThreshold))
        {
            ArticleFinder articleFinder = new ArticleFinder();
            articlesToChooseFrom = articleFinder.GetMatchingArticles(Main.userConnected);
        }

        //List<Article> unClassifiedArticales = DBConnection.GetAllUnclassifiedArticlesForUser(Main.userConnected);
        Random myRandomizer = new Random();

        Article FirstRandomArticle = articlesToChooseFrom.get(myRandomizer.nextInt(articlesToChooseFrom.size()));
        Article SecondRandomArticle = articlesToChooseFrom.get(myRandomizer.nextInt(articlesToChooseFrom.size()));

        FirstArticle = new JTextArea(FirstRandomArticle.text.toString().substring(0,300) + ".....", 40, 40);
        Border border1 = BorderFactory.createLineBorder(Color.BLACK);
        FirstArticle.setBorder(BorderFactory.createCompoundBorder(border1,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        FirstArticle.setLineWrap(true);
        FirstArticle.setWrapStyleWord(true);
            //panel.add(new JScrollPane(FirstArticle), BorderLayout.CENTER);
        panel.add(FirstArticle, BorderLayout.WEST);
        panel.add(FirstExpand, BorderLayout.EAST);
        //FirstArticle.setText(FirstRandomArticle.text.toString());
        FirstExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArticleDisplay articleDisplay = new ArticleDisplay(FirstRandomArticle);
                articleDisplay.setVisible(true);
                setVisible(false);
            }
        });

        SecondArticle = new JTextArea(SecondRandomArticle.text.toString().substring(0,300) + ".....", 40, 40);
        Border border2 = BorderFactory.createLineBorder(Color.BLACK);
        SecondArticle.setBorder(BorderFactory.createCompoundBorder(border2,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        SecondArticle.setLineWrap(true);
        SecondArticle.setWrapStyleWord(true);
        //panel.add(new JScrollPane(SecondArticle), BorderLayout.CENTER);
        panel.add(SecondArticle, BorderLayout.WEST);
        panel.add(SecondExpand, BorderLayout.EAST);

        panel.add(ReLoad,BorderLayout.WEST);
        //SecondArticle.setText(SecondRandomArticle.text.toString());
        SecondExpand.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ArticleDisplay articleDisplay = new ArticleDisplay(SecondRandomArticle);
                articleDisplay.setVisible(true);
                setVisible(false);
            }
        });

        ReLoad.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ArticleMainDisplay articleDisplay = null;
                try {
                    articleDisplay = new ArticleMainDisplay();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                articleDisplay.setVisible(true);
                setVisible(false);
            }
        });
        /*
        JScrollPane sp1 = new JScrollPane(FirstArticle);
        sp1.setBounds(10, 60, 780, 500);
        sp1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(sp1);
        JScrollPane sp2 = new JScrollPane(SecondArticle);
        sp2.setBounds(10, 60, 780, 500);
        sp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(sp2);
        */

        pack();

        setVisible(true);


        setSize(1500,1500);

    }


}
