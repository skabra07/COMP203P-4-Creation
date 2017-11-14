package ucl.cs;

import java.util.Collection;
import java.util.List;

/**
 * Created by Suyash on 14-Nov-17.
 */
public interface LibraryCatalogue {
    List<Book> searchFor(String query);
    Collection<Book> allTheBooks();
}
