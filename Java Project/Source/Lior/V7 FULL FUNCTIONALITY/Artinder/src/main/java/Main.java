import javax.swing.*;
import javax.swing.UIManager.*;
import javax.swing.plaf.metal.MetalRootPaneUI;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;

/**
 * Created by Lior on 08/04/2015.
 */
public class Main {

    public static int ClassificationThreshold = 10;
    public static String userConnected;
    public class AdvancedNimbusLookAndFeel extends NimbusLookAndFeel {

        @Override
        public boolean getSupportsWindowDecorations() {
            return true;
        }

    }
public static void main (String[] args) throws Exception {

    UIManager.installLookAndFeel("AdvancedNimbus", AdvancedNimbusLookAndFeel.class.getName());
    UIManager.put("Button.background", Color.ORANGE);
    UIManager.put("Button.foreground", Color.black);
    UIManager.put("Panel.background", Color.gray);
    UIManager.put("Button.foreground", Color.black);
    UIManager.put("TextArea.border", Color.BLACK);
    UIManager.put("TextArea.foreground", Color.white);
    UIManager.put("TextArea.background", Color.gray);
    UIManager.put("Label.background", Color.DARK_GRAY);
    UIManager.put("Text.background", Color.white);
    UIManager.put("defaultFont", new Font("Ariel", Font.BOLD, 14));

    for( javax.swing.UIManager.LookAndFeelInfo info: javax.swing.UIManager.getInstalledLookAndFeels()) {
        if( "metal".equals( info.getName() ) ) {// --------> Doesn't change nothing!
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
        MainPage page = new MainPage();
  //  } else
   // {
        //show article page
    //}
    }
}
