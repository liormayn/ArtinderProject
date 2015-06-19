import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Lior on 08/04/2015.
 */
public class MainPage extends JFrame{
    private JButton loginButton;
    private JButton registerButton;
    private JPanel MainPagePanel;

    public MainPage() {
        super();
        this.setContentPane(MainPagePanel);
        loginButton.addActionListener(LoginClicked());
        registerButton.addActionListener(RegisterClicked());
        pack();
        setVisible(true);
    }

    private ActionListener LoginClicked() {
        return e -> {
            LoginPage page = new LoginPage();
            //this.hide?
        };
    }

    private ActionListener RegisterClicked() {
        return e -> {
            RegisterPage page = new RegisterPage();
            //this.hide?
        };
    }
}
