
import javax.swing.*;
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

    public ArticleMainDisplay() throws SQLException, ClassNotFoundException {
        super();
        panel = new JPanel();
        this.setContentPane(panel);

        //setVisible(true);
        // Show 2 random articles

        List<Article> unClassifiedArticales = DBConnection.GetAllUnclassifiedArticlesForUser(Main.userConnected);
        Random myRandomizer = new Random();

        Article FirstRandomArticle = unClassifiedArticales.get(myRandomizer.nextInt(unClassifiedArticales.size()));
        Article SecondRandomArticle = unClassifiedArticales.get(myRandomizer.nextInt(unClassifiedArticales.size()));

        FirstArticle = new JTextArea(FirstRandomArticle.text.toString(), 40, 40);
        FirstArticle.setLineWrap(true);
        FirstArticle.setWrapStyleWord(true);
            panel.add(new JScrollPane(FirstArticle), BorderLayout.CENTER);
        panel.add(FirstExpand,BorderLayout.EAST);
        //FirstArticle.setText(FirstRandomArticle.text.toString());
        FirstExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArticleDisplay articleDisplay = new ArticleDisplay(FirstRandomArticle);
                articleDisplay.setVisible(true);
                setVisible(false);
            }
        });

        SecondArticle = new JTextArea(SecondRandomArticle.text.toString(), 40, 40);
        SecondArticle.setLineWrap(true);
        SecondArticle.setWrapStyleWord(true);
        panel.add(new JScrollPane(SecondArticle), BorderLayout.CENTER);
        panel.add(SecondExpand,BorderLayout.EAST);
        //SecondArticle.setText(SecondRandomArticle.text.toString());
        SecondExpand.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ArticleDisplay articleDisplay = new ArticleDisplay(SecondRandomArticle);
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

    }


}
