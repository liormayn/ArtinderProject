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
    private JCheckBox localConnection;

    public MainPage(){
        super();
        this.setResizable(false);
        this.setContentPane(MainPagePanel);
        this.setTitle("Artinder");
        loginButton.addActionListener(LoginClicked());
        registerButton.addActionListener(RegisterClicked());
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(loginButton);
        this.setLocationRelativeTo(null);

        localConnection.setSelected(DBConnection.IsConnectionLocal());
        setVisible(true);

    }
    private ActionListener LoginClicked() {
        return e -> {
            DBConnection.SetConnectionType(localConnection.isSelected());
            LoginPage page = new LoginPage();
            dispose();
            //this.hide?
        };
    }

    private ActionListener RegisterClicked() {
        return e -> {
            DBConnection.SetConnectionType(localConnection.isSelected());
            RegisterPage page = new RegisterPage();
            dispose();
            //this.hide?
        };
    }
}
