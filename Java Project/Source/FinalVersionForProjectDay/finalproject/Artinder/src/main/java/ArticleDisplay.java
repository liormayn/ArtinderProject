import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
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
    private JTextPane textPane2;

    public ArticleDisplay(Article article){

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

        Border border1 = BorderFactory.createLineBorder(Color.LIGHT_GRAY);

        String ArticleTitle = article.text.substring(0, article.text.indexOf("\r\n"));
        String ArticleBody = article.text.substring(article.text.indexOf("\r\n\r\n"));



        textPane2 = new JTextPane();
        textPane2.setBorder(BorderFactory.createCompoundBorder(border1,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        textPane2.setEditable(false);
        StyledDocument doc2 = textPane2.getStyledDocument();
        Style style2 = textPane2.addStyle("Style2", null);
        StyleConstants.setFontSize(style2, 18);
        StyleConstants.setBold(style2, true);

        try { doc2.insertString(doc2.getLength(), ArticleTitle ,style2); }
        catch (BadLocationException e){}
        StyleConstants.setFontSize(style2, 16);
        StyleConstants.setBold(style2, false);
        try { doc2.insertString(doc2.getLength(), ArticleBody, style2); }
        catch (BadLocationException e){}
        textPane2.setSize(new Dimension(295, 360));
        textPane2.setPreferredSize(new Dimension(295, 360));
        textPane2.setAutoscrolls(true);

        panel.add(new JScrollPane(textPane2), BorderLayout.SOUTH);

        likeButton.setSize(20, 10);
        likeButton.setBackground(Color.WHITE);


        unlikeButton.setBackground(Color.WHITE);
        unlikeButton.setSize(20,10);

        backpanel.add(BackButton, BorderLayout.EAST);

        spanel.add(likeButton, BorderLayout.EAST);
        spanel.add(unlikeButton, BorderLayout.WEST);

        spanel.add(backpanel, BorderLayout.CENTER);

        mainpanel.add(logopanel, BorderLayout.NORTH);
        mainpanel.add(panel, BorderLayout.CENTER);
        mainpanel.add(spanel, BorderLayout.SOUTH);

        this.setContentPane(mainpanel);
        getRootPane().setDefaultButton(BackButton);
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
                dispose();
            }
        });


        pack();

        this.setLocationRelativeTo(null);

        setSize(740, 650);
        setResizable(false);
        setMinimumSize(getSize());
        setVisible(true);


    }


}
