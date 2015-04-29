import javax.swing.*;

/**
 * Created by Lior on 08/04/2015.
 */
public class ArticleMainDisplay extends JFrame{

    private JPanel panel;
    private JTextField FirstArticle;
    private JTextField SecondArticle;
    private JButton FirstExpand;
    private JButton SecondExpand;

    public ArticleMainDisplay() {
        super();
        this.setContentPane(panel);
        pack();
        setVisible(true);
    }

}
