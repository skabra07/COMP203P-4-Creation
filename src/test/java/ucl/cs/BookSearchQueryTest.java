package ucl.cs;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Test;
import ucl.cs.catalogues.BritishLibraryCatalogue;

import static ucl.cs.catalogues.BookSearchQueryBuilder.*;

public class BookSearchQueryTest {

  @Test
  public void searchesForBooksInLibraryCatalogueByAuthorSurname() {
    BookSearchQuery query = aBook().withName2("dickens").build();
    List<Book> books = query.execute(BritishLibraryCatalogue.getInstance());
    assertThat(books.size(), is(2));
    assertTrue(books.get(0).matchesAuthor("dickens"));
  }

  @Test
  public void searchesForBooksInLibraryCatalogueByAuthorFirstname() {
    BookSearchQuery query = aBook().withName1("Jane").build();
    List<Book> books = query.execute(BritishLibraryCatalogue.getInstance());
    assertThat(books.size(), is(2));
    assertTrue(books.get(0).matchesAuthor("Austen"));
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
