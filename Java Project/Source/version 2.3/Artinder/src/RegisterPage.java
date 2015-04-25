import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lior on 08/04/2015.
 */
public class RegisterPage extends JFrame{
    private JTextArea loginTextArea;
    private JTextField usernameLabel;
    private JButton loginButton;
    private JPasswordField passwordField;
    private JTextField usernameField;
    private JTextField passwordLabel;
    private JPanel PanelLior;
    private JButton backButton;
    //adding age to calculation?


    public RegisterPage() {
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
                JOptionPane.showMessageDialog(null, "Not all fields are valid");
            } else {
                JOptionPane.showMessageDialog(null, "You are not registered " + usernameField.getText() + " !");
                //save user to DB.
                ArticleMainDisplay page = new ArticleMainDisplay();
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