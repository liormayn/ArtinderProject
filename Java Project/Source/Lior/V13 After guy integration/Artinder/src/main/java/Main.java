import javax.swing.*;
import javax.swing.UIManager.*;
import javax.swing.plaf.metal.MetalRootPaneUI;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.sql.SQLException;

/**
 * Created by Lior on 08/04/2015.
 */

public class Main {

    public static int ClassificationThreshold = 2;
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
        MainPage page = null;
        try {
            // Runtime.getRuntime().addShutdownHook(new TerminateProgram());
            UIManager.installLookAndFeel("AdvancedNimbus", AdvancedNimbusLookAndFeel.class.getName());
            //UIManager.put("Button.background", Color.CYAN);
            //UIManager.put("Button.foreground", Color.black);
            //UIManager.put("Panel.background", Color.gray);
            //UIManager.put("Button.foreground", Color.black);
            //UIManager.put("TextArea.border", Color.BLACK);
            //UIManager.put("TextArea.foreground", Color.white);
            //UIManager.put("TextArea.background", Color.gray);
            //UIManager.put("Label.background", Color.DARK_GRAY);
            //UIManager.put("Text.background", Color.white);
            UIManager.put("defaultFont", new Font("Ariel", Font.BOLD, 14));

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {// --------> Doesn't change nothing!
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            JFrame.setDefaultLookAndFeelDecorated(true);
            //////////////////////////
            // if not logged in

            //String[] arr = new String[] {"C:\\Temp\\FinalProject\\artinder","C:\\Temp\\FinalProject\\Classify"};
            //TextClassifier txt = new TextClassifier();
            //txt.main(arr);


            // DBConnection wk = new DBConnection();

            // wk.RegisterUser("hello","hello");
            // wk.GetAllArticles();
            // if (wk.CanUserConnect("lior","lior"))
            // {
            //     System.out.println("connected");
            // }

            // if (wk.CanUserConnect("lior","sdfs"))
            // {
            //     System.out.println("not conncted");
            // }

            //  if (true) {
            page = new MainPage();
        } finally {
            // TerminateProgram terminate = new TerminateProgram();
            // terminate.run();
        }
        //  } else
        // {
        //show article page
        //}
    }
}
