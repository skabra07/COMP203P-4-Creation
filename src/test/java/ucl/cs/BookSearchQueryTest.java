package ucl.cs;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import ucl.cs.catalogues.BritishLibraryCatalogue;
import static ucl.cs.catalogues.BookSearchQueryBuilder.*;

public class BookSearchQueryTest {

  private static final List<Book> BOOKS = Arrays.asList(new Book("A Christmas Carol", "Charles Dickens", 1835));
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private LibraryCatalogue catalogue = context.mock(LibraryCatalogue.class);

  @Test
  public void searchesForBooksInLibraryCatalogueByAuthorSurname() {
    // We use the LibraryCatalogue interface to test for any libraries that implement it. Not specific test to BritishLibrary.
    // Since we haven't implemented the searchFor function in LibraryCatalogue interface we will make a mock object of it and use
    // checking to say what it will do

    context.checking(new Expectations() {{
      // We check that catalogue class calls the searchFor function with the query exactly once. If it does it will return the BOOKS array
      exactly(1).of(catalogue).searchFor("LASTNAME='dickens' "); will(returnValue(BOOKS));
    }});

    // get all books with the surname as dickens in the catalogue library
    List<Book> books = aBook().withName2("dickens").build().execute(catalogue);

    // since we said that catalogue.searchFor() will return BOOKs if the input parameter is dickens, we check if it does that or not.
    assertThat(books, is(BOOKS));
  }


  @Test
  public void searchesForBooksInLibraryCatalogueByAuthorFirstname() {
    // since we know that the assetThat works for the first test, we just need to check that catalogue.searchFor is called only once with the
    // parameter "FIRSTNAME='Jane'
    context.checking(new Expectations() {{
      exactly(1).of(catalogue).searchFor("FIRSTNAME='Jane' ");
    }});
    List<Book> books = aBook().withName1("Jane").build().execute(catalogue);
  }

  @Test
  public void searchesForBooksInLibraryCatalogueByTitle() {
    BookSearchQuery query = aBook().withTitle("Two Cities").build();
    List<Book> books = query.execute(BritishLibraryCatalogue.getInstance());
    assertThat(books.size(), is(1));
    assertTrue(books.get(0).matchesAuthor("dickens"));
  }

  @Test
  public void searchesForBooksInLibraryCatalogueBeforeGivenPublicationYear() {
    BookSearchQuery query = aBook().withDate2(1700).build();
    List<Book> books = query.execute(BritishLibraryCatalogue.getInstance());
    assertThat(books.size(), is(1));
    assertTrue(books.get(0).matchesAuthor("Shakespeare"));
  }

  @Test
  public void searchesForBooksInLibraryCatalogueAfterGivenPublicationYear() {
    BookSearchQuery query = aBook().withDate1(1950).build();
    List<Book> books = query.execute(BritishLibraryCatalogue.getInstance());
    assertThat(books.size(), is(1));
    assertTrue(books.get(0).matchesAuthor("Golding"));
  }

  @Test
  public void searchesForBooksInLibraryCatalogueWithCombinationOfParameters() {
    BookSearchQuery query = aBook().withName2("dickens").withDate2(1840).build();
    List<Book> books = query.execute(BritishLibraryCatalogue.getInstance());
    assertThat(books.size(), is(1));
    assertTrue(books.get(0).matchesAuthor("charles dickens"));
  }
}
