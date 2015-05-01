import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
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

    public ArticleDisplay(Article article){
            super();
            panel = new JPanel();
            Article = new JTextArea(article.text.toString(), 40, 40);
            Article.setLineWrap(true);
            Article.setWrapStyleWord(true);
            panel.add(new JScrollPane(Article), BorderLayout.CENTER);
            panel.add(likeButton, BorderLayout.SOUTH);
            panel.add(unlikeButton, BorderLayout.SOUTH);
            this.setContentPane(panel);

            likeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        DBConnection.SetClassification(Main.userConnected, article.id, true);
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
                    }
                    catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e2) {
                        e2.printStackTrace();
                    }
                }
            });
            pack();
            setVisible(true);
        }


}
