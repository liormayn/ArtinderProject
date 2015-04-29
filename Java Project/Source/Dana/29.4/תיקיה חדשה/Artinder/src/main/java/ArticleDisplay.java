import javax.swing.*;

/**
 * Created by Lior on 25/04/2015.
 */
public class ArticleDisplay  extends JFrame{

    private JPanel panel;
    private JTextField Article;
    private JButton likeButton;
    private JButton unlikeButton;

    public ArticleDisplay() {
            super();
            this.setContentPane(panel);
            pack();
            setVisible(true);
        }


}
