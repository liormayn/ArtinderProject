import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;


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


    public RegisterPage() {
        super();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setContentPane(PanelLior);
        this.setTitle("Artinder");
        loginButton.addActionListener(LoginClicked());
        backButton.addActionListener(BackClicked());
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        getRootPane().setDefaultButton(loginButton);
        setVisible(true);
    }

    private ActionListener LoginClicked() {
        return e -> {
            String password = new String(passwordField.getPassword());
            try
            {
                if ((usernameField.getText().isEmpty()) || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Not all fields are valid");
                }

                else if (DBConnection.DoesUserExist(usernameField.getText().toString()))
                {
                    JOptionPane.showMessageDialog(null, "The username you chose already exists, please choose a different one.");
                }
            else
            {
                // Registering the user to the db
                DBConnection.RegisterUser(usernameField.getText().toString(),passwordField.getText().toString());


                Main.userConnected = usernameField.getText().toString();
                //yes? page of articles
                ArticleMainDisplay page = new ArticleMainDisplay();
                JOptionPane.showMessageDialog(null, "Registered successfully! " + Main.userConnected);
                dispose();
            }
        }
        catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        };
        }

    private ActionListener BackClicked() {
        return e -> {
            MainPage page = new MainPage();
            dispose();
        };

    }
}