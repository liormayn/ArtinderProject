import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.sql.SQLException;

/**
 * Created by Lior on 08/04/2015.
 */

public class Main {

    public static int PositiveClassificationThreshold = 20;
    public static int NegativeClassificationThreshold = 50;
    public static String userConnected;

    public class AdvancedNimbusLookAndFeel extends NimbusLookAndFeel {

        @Override
        public boolean getSupportsWindowDecorations() {
            return true;
        }

    }

    // a class that extends thread that is to be called when program is exiting
    static class TerminateProgram extends Thread {

        public void run() {
            try {
                DBConnection.CloseConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws Exception {

            UIManager.installLookAndFeel("AdvancedNimbus", AdvancedNimbusLookAndFeel.class.getName());
            UIManager.put("defaultFont", new Font("Ariel", Font.BOLD, 14));

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {// --------> Doesn't change nothing!
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            JFrame.setDefaultLookAndFeelDecorated(true);


        MainPage page = new MainPage();

    }
}
