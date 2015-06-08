
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private JLabel lillogo;

    public ArticleMainDisplay() throws Exception {
        super();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(6, 6, 6, 6));
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel(new BorderLayout());
        panel3.setBorder(new EmptyBorder(6, 6, 6, 6));
        JPanel panelfirst = new JPanel();
        JPanel panelsec = new JPanel();

        panel.setBackground(Color.white);
        panel2.setBackground(Color.white);
        panel3.setBackground(Color.white);
        panelfirst.setBackground(Color.white);
        panelsec.setBackground(Color.white);

        this.setContentPane(panel3);

        this.setTitle("Artinder");

        List<Article> articlesToChooseFrom = DBConnection.GetAllUnclassifiedArticlesForUser(Main.userConnected);

        if ((DBConnection.GetPositiveClassificationCount(Main.userConnected) > Main.ClassificationThreshold)
                && (DBConnection.GetNegativeClassificationCount(Main.userConnected) >  Main.ClassificationThreshold))
        {
            ArticleFinder articleFinder = new ArticleFinder();
            articlesToChooseFrom = articleFinder.GetMatchingArticles(Main.userConnected);
        }

        Random myRandomizer = new Random();

        Article FirstRandomArticle = articlesToChooseFrom.get(myRandomizer.nextInt(articlesToChooseFrom.size()));
        Article SecondRandomArticle = articlesToChooseFrom.get(myRandomizer.nextInt(articlesToChooseFrom.size()));

        String FirstArticleTitle = FirstRandomArticle.text.substring(0, FirstRandomArticle.text.indexOf("\r\n"));
        String SecondArticleTitle = SecondRandomArticle.text.substring(0, SecondRandomArticle.text.indexOf("\r\n"));

        String FirstArticlePreviewContent =  FirstRandomArticle.text.substring(FirstRandomArticle.text.indexOf("\r\n"), Math.min(200, (FirstRandomArticle.text.length() - FirstArticleTitle.length() - 4)/2)).toString().trim();
        String SecondArticlePreviewContent = SecondRandomArticle.text.substring(SecondRandomArticle.text.indexOf("\r\n"), Math.min(200, (SecondRandomArticle.text.length()- SecondArticleTitle.length() - 4) / 2)).toString().trim();
       // String FirstArticlePreviewContent =  FirstRandomArticle.text.substring(FirstRandomArticle.text.indexOf("\r\n"),200).toString().trim();
       // String SecondArticlePreviewContent = SecondRandomArticle.text.substring(SecondRandomArticle.text.indexOf("\r\n"),200).toString().trim();


        FirstArticle = new JTextArea(FirstArticleTitle + "\r\n\r\n" + FirstArticlePreviewContent + ".....", 10, 25);
        Border border1 = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        FirstArticle.setBorder(BorderFactory.createCompoundBorder(border1,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        FirstArticle.setLineWrap(true);
        FirstArticle.setWrapStyleWord(true);
        FirstArticle.setEditable(false);
        //panel.add(new JScrollPane(FirstArticle), BorderLayout.CENTER);

        panel.add(FirstArticle, BorderLayout.WEST);
        //panel2.add(FirstExpand, BorderLayout.WEST);
        panelfirst.add(FirstExpand, BorderLayout.WEST);

        //panel.add(FirstExpand, BorderLayout.SOUTH);
        //FirstArticle.setText(FirstRandomArticle.text.toString());
        FirstExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArticleDisplay articleDisplay = new ArticleDisplay(FirstRandomArticle);
                articleDisplay.setVisible(true);
                setVisible(false);
            }
        });

        SecondArticle = new JTextArea((SecondArticleTitle + "\r\n\r\n" + SecondArticlePreviewContent + ".....").toString(), 10, 25);
        Border border2 = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        SecondArticle.setBorder(BorderFactory.createCompoundBorder(border2,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        SecondArticle.setLineWrap(true);
        SecondArticle.setWrapStyleWord(true);
        SecondArticle.setEditable(false);
        //panel.add(new JScrollPane(SecondArticle), BorderLayout.CENTER);

        panel2.add(ReLoad);//, BorderLayout.CENTER);

        panel.add(lillogo, BorderLayout.CENTER);
        panel.add(SecondArticle, BorderLayout.EAST);

        panelsec.add(SecondExpand);//, BorderLayout.EAST);

        panel3.add(panel, BorderLayout.NORTH);
        panel3.add(panel2, BorderLayout.CENTER);
        panel3.add(panelfirst, BorderLayout.WEST);
        panel3.add(panelsec, BorderLayout.EAST);

        //panel.add(SecondArticle, BorderLayout.WEST);
        //panel.add(SecondExpand, BorderLayout.EAST);
        //panel.add(ReLoad, BorderLayout.WEST);

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

        this.setLocationRelativeTo(null);

        setSize(740, 290);
        setMinimumSize(getSize());
        setResizable(false);

        setBackground(Color.WHITE);
        setVisible(true);


        //setSize(1500,1500);

    }


}
