
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JLabel Logout;
    private JTextPane textPane1;
    private JTextPane textPane2;

    public ArticleMainDisplay() throws Exception {
        super();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        List<Article> articlesToChooseFrom;
        if ((DBConnection.GetPositiveClassificationCount(Main.userConnected) > Main.PositiveClassificationThreshold)
                && (DBConnection.GetNegativeClassificationCount(Main.userConnected) >  Main.NegativeClassificationThreshold))
        {
            ArticleFinder articleFinder = new ArticleFinder();
            articlesToChooseFrom = articleFinder.GetMatchingArticles(Main.userConnected);
        }
        else {
            articlesToChooseFrom = DBConnection.GetAllUnclassifiedArticlesForUser(Main.userConnected);
        }

        Random myRandomizer = new Random();
        int randomFirstId = myRandomizer.nextInt(articlesToChooseFrom.size());
        Article FirstRandomArticle = articlesToChooseFrom.get(randomFirstId);

        int randomSecondtId = (myRandomizer.nextInt(articlesToChooseFrom.size()));
        while (articlesToChooseFrom.size() > 2 && randomFirstId == randomSecondtId)
        {
            randomSecondtId = (myRandomizer.nextInt(articlesToChooseFrom.size()));
        }
        Article SecondRandomArticle = articlesToChooseFrom.get(myRandomizer.nextInt(articlesToChooseFrom.size()));

        String FirstArticleTitle = FirstRandomArticle.text.substring(0, FirstRandomArticle.text.indexOf("\r\n"));
        String SecondArticleTitle = SecondRandomArticle.text.substring(0, SecondRandomArticle.text.indexOf("\r\n"));

        String FirstArticlePreviewContent =  FirstRandomArticle.text.substring(FirstRandomArticle.text.indexOf("\r\n"), Math.min(200, FirstRandomArticle.text.length() - FirstArticleTitle.length() - 4/2)).toString().trim();
        String SecondArticlePreviewContent = SecondRandomArticle.text.substring(SecondRandomArticle.text.indexOf("\r\n"), Math.min(200, SecondRandomArticle.text.length() -SecondArticleTitle.length() -  4/2)).toString().trim();


        Border border1 = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

        textPane1 = new JTextPane();
        textPane1.setBorder(BorderFactory.createCompoundBorder(border1,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        textPane1.setEditable(false);
        StyledDocument doc = textPane1.getStyledDocument();
        Style style = textPane1.addStyle("Style1", null);
        StyleConstants.setFontSize(style, 14);
        StyleConstants.setBold(style, true);
        try { doc.insertString(doc.getLength(), FirstArticleTitle ,style); }
        catch (BadLocationException e){}
        StyleConstants.setBold(style, false);
        try { doc.insertString(doc.getLength(), "\r\n\r\n" + FirstArticlePreviewContent  + ".....", style); }
        catch (BadLocationException e){}
        textPane1.setSize(new Dimension(295, Integer.MAX_VALUE));
        textPane1.setPreferredSize(new Dimension(295, 200));


        textPane2 = new JTextPane();
        textPane2.setBorder(BorderFactory.createCompoundBorder(border1,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        textPane2.setEditable(false);
        StyledDocument doc2 = textPane2.getStyledDocument();
        Style style2 = textPane2.addStyle("Style2", null);
        StyleConstants.setFontSize(style2, 14);
        StyleConstants.setBold(style2, true);
        try { doc2.insertString(doc2.getLength(), SecondArticleTitle ,style2); }
        catch (BadLocationException e){}
        StyleConstants.setBold(style2, false);
        try { doc2.insertString(doc2.getLength(), "\r\n\r\n" + SecondArticlePreviewContent  + ".....", style2); }
        catch (BadLocationException e){}
        textPane2.setSize(new Dimension(295, Integer.MAX_VALUE));
        textPane2.setPreferredSize(new Dimension(295, 200));

        panel.add(textPane1, BorderLayout.WEST);

        panelfirst.add(FirstExpand, BorderLayout.WEST);


        FirstExpand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArticleDisplay articleDisplay = new ArticleDisplay(FirstRandomArticle);
                articleDisplay.setVisible(true);


            }
        });

        panel2.add(ReLoad);



        panel.add(lillogo, BorderLayout.CENTER);
        panel2.add(Logout, BorderLayout.SOUTH);

        panel.add(textPane2, BorderLayout.EAST);

        panelsec.add(SecondExpand);//, BorderLayout.EAST);

        panel3.add(panel, BorderLayout.NORTH);
        panel3.add(panel2, BorderLayout.CENTER);
        panel3.add(panelfirst, BorderLayout.WEST);
        panel3.add(panelsec, BorderLayout.EAST);

        SecondExpand.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                ArticleDisplay articleDisplay = new ArticleDisplay(SecondRandomArticle);
                articleDisplay.setVisible(true);


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
                dispose();
            }
        });

        getRootPane().setDefaultButton(ReLoad);

        Logout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int choice = JOptionPane.showConfirmDialog(null,
                        "האם אתה בטוח שברצונך להתנתק?", "יציאה", JOptionPane.YES_NO_OPTION);
                if(choice == 0 ){
                    Main.userConnected = "";
                    Main.firstUseAfterLogin = true;
                    setVisible(false);
                    MainPage mainPage = new MainPage();
                    dispose();
                }

            }
        });

        pack();

        this.setLocationRelativeTo(null);
        setResizable(false);
        setSize(740, 300);
        setMinimumSize(getSize());
        setResizable(false);

        setBackground(Color.WHITE);
        setVisible(true);


    }


}
