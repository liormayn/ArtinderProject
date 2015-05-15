import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by Lior on 25/04/2015.
 */
public class ArticleDisplay  extends JFrame{

    private JPanel panel;
    private JButton likeButton;
    private JButton unlikeButton;
    private JTextArea Article;
    private JButton BackButton;
    //private String imgCrossFilename = "C:\\Users\\ronen\\Desktop\\Like.jpg";

    public ArticleDisplay(Article article){

            //ImageIcon iconNought = null;
            this.setTitle("Artinder");
            panel = new JPanel();
            Article = new JTextArea(article.text.toString(), 40, 40);
            Border border1 = BorderFactory.createLineBorder(Color.BLACK);
            Article.setBorder(BorderFactory.createCompoundBorder(border1,
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            Article.setLineWrap(true);
            Article.setWrapStyleWord(true);
            panel.add(new JScrollPane(Article), BorderLayout.CENTER);
            //likeButton.setBackground(Color.cyan);


            likeButton.setSize(20, 10);
            likeButton.setBackground(Color.GRAY);

           
            unlikeButton.setBackground(Color.GRAY);
            unlikeButton.setSize(20,10);
        /*
            URL imgURL = getClass().getClassLoader().getResource(imgCrossFilename);
             if (imgURL != null) {
                 iconNought = new ImageIcon(imgURL);
             } else {
                System.err.println("Couldn't find file: " + imgCrossFilename);
             }
             */
            //likeButton.setIcon(iconNought);
            //unlikeButton.setBackground(Color.red);
            panel.add(likeButton, BorderLayout.SOUTH);
            panel.add(unlikeButton, BorderLayout.SOUTH);
        panel.add(BackButton,BorderLayout.SOUTH);
            this.setContentPane(panel);

            likeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        DBConnection.SetClassification(Main.userConnected, article.id, true);
                        JOptionPane.showMessageDialog(null, "You like the article");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e2) {
                        e2.printStackTrace();
                    }
                }
            });

            unlikeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        DBConnection.SetClassification(Main.userConnected, article.id, false);
                        JOptionPane.showMessageDialog(null, "You unlike the article");
                    }
                    catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e2) {
                        e2.printStackTrace();
                    }
                }
            });

        BackButton.addActionListener(new ActionListener() {

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
            pack();
            setVisible(true);
            setSize(1000, 1000);

    }


}
