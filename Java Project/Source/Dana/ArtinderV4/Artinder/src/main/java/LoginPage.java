import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
        this.setTitle("Artinder");
        usernameLabel.setBackground(Color.DARK_GRAY);
        passwordLabel.setBackground(Color.DARK_GRAY);
        usernameField.setBackground(Color.GRAY);
        passwordField.setBackground(Color.GRAY);
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
                //Check if user exists in db.
                try {
                    if (DBConnection.CanUserConnect(usernameField.getText().toString(),passwordField.getText().toString()))
                    {
                        //yes? page of articles
                        ArticleMainDisplay page = new ArticleMainDisplay();

                        Main.userConnected = usernameField.getText().toString();
                        JOptionPane.showMessageDialog(null, "Connected " + Main.userConnected);
                        setVisible(false);

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Connection failed! Please check your username and password");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }


            }
        };
    }
    private ActionListener BackClicked() {
        return e -> {
            MainPage page = new MainPage();
            setVisible(false);
        };

    }

}

