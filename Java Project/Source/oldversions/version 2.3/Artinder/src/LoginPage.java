import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lior on 08/04/2015.
 */
public class LoginPage extends JFrame{
    private JTextArea loginTextArea;
    private JTextField usernameLabel;
    private JButton loginButton;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JTextField passwordLabel;
    private JPanel PanelLior;
    private JButton backButton;

    public LoginPage() {
        super();
        this.setContentPane(PanelLior);
        loginButton.addActionListener(LoginClicked());
        backButton.addActionListener(BackClicked());
        pack();
        setVisible(true);
    }

    private ActionListener LoginClicked() {
        return e -> {
            String password = new String(passwordField.getPassword());
            if ((usernameField.getText().isEmpty()) || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please insert your login credentials.");
            } else {
                JOptionPane.showMessageDialog(null, "Welcome! " + usernameField.getText() + " With Password " + passwordField.getText());
                //Check if user exists in db.
                if (true)
                {
                    //yes? page of articles
                    ArticleMainDisplay page = new ArticleMainDisplay();
                }
                else
                {
                    // no? message.
                }



            }
        };
    }
    private ActionListener BackClicked() {
        return e -> {
            MainPage page = new MainPage();
            //this.hide?
        };

    }

}

