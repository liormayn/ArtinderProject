import java.lang.Object;
import java.lang.Override;

/**
 * Created by Lior on 25/04/2015.
 */
public class Article {
    public int id;
    public String text;

    @Override
    public boolean equals(Object obj) {
        if (((Article) obj) == null) return false;
        if (((((Article) obj).id == this.id)) && (((Article) obj).text.equals(this.text))) return true;

        return false;
    }
}
