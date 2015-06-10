import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
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
    private JLabel lillogo;
    //private String imgCrossFilename = "C:\\Users\\ronen\\Desktop\\Like.jpg";

    public ArticleDisplay(Article article){

            //ImageIcon iconNought = null;
            this.setTitle("Artinder");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(6, 6, 6, 6));
        JPanel mainpanel = new JPanel(new BorderLayout());
        mainpanel.setBorder(new EmptyBorder(6, 6, 6, 6));
        JPanel spanel = new JPanel(new BorderLayout());
        spanel.setBorder(new EmptyBorder(6, 6, 6, 6));
        JPanel logopanel = new JPanel();
        JPanel backpanel = new JPanel();

        panel.setBackground(Color.white);
        mainpanel.setBackground(Color.white);
        spanel.setBackground(Color.white);
        logopanel.setBackground(Color.white);
        backpanel.setBackground(Color.white);

        logopanel.add(lillogo);

            Article = new JTextArea(article.text.toString(), 20, 40);
            Border border1 = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
            Article.setBorder(BorderFactory.createCompoundBorder(border1,
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            Article.setLineWrap(true);
            Article.setWrapStyleWord(true);
        Article.setMaximumSize(getMaximumSize());

            Article.setEditable(false);


            panel.add(new JScrollPane(Article), BorderLayout.SOUTH);
            //likeButton.setBackground(Color.cyan);


            likeButton.setSize(20, 10);
            likeButton.setBackground(Color.WHITE);

           
            unlikeButton.setBackground(Color.WHITE);
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
        backpanel.add(BackButton, BorderLayout.EAST);

            spanel.add(likeButton, BorderLayout.EAST);
            spanel.add(unlikeButton, BorderLayout.WEST);

        spanel.add(backpanel, BorderLayout.CENTER);

        mainpanel.add(logopanel, BorderLayout.NORTH);
            mainpanel.add(panel, BorderLayout.CENTER);
            mainpanel.add(spanel, BorderLayout.SOUTH);

            this.setContentPane(mainpanel);

            likeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        DBConnection.SetClassification(Main.userConnected, article.id, true);
                        JOptionPane.showMessageDialog(null, "You Liked the Article!");
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
                        JOptionPane.showMessageDialog(null, "You Disliked the Article!");
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
              //  ArticleMainDisplay articleDisplay = null;
               // try {
               //     articleDisplay = new ArticleMainDisplay();
               // } catch (Exception e1) {
               //     e1.printStackTrace();
                //}
                //articleDisplay.setVisible(true);
                dispose();
            }
        });

        pack();

        this.setLocationRelativeTo(null);

        setSize(740, 650);
        setMinimumSize(getSize());
        setVisible(true);


    }


}
