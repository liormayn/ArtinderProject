import javax.swing.*;

/**
 * Created by Lior on 08/04/2015.
 */
public class Main {

public static void main (String[] args) throws Exception {
    // if not logged in

    String[] arr = new String[] {"C:\\Temp\\FinalProject\\artinder","C:\\Temp\\FinalProject\\Classify"};
    TextClassifier txt = new TextClassifier();
    txt.main(arr);

    //WekaTextClassifier wk = new WekaTextClassifier();
    //wk.main(arr);

    if (true) {
        MainPage page = new MainPage();
    } else
    {
        //show article page
    }
    }
}
