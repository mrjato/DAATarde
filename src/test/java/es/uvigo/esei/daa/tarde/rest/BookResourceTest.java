package es.uvigo.esei.daa.tarde.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.daos.BookDAO;
import es.uvigo.esei.daa.tarde.entities.Book;

@RunWith(Parameterized.class)
public class BookResourceTest extends BaseResourceTest {

    @Parameters(name = "{index}: {0}")
    public static final Collection<Object[ ]> createBookData( ) {
        return Arrays.asList(new Object[ ][ ] {
            { "haskell", new Book[ ] {
                new Book("Learn You a Haskell For Great Good",          new LocalDate(2011,  4, 18)),
                new Book("Real World Haskell",                          new LocalDate(2008, 12,  5)),
                new Book("Beginning Haskell: A Project-Based Approach", new LocalDate(2014,  1, 22))
            }},
            { "scala", new Book[ ] {
                new Book("Programming in Scala",                                 new LocalDate(2011,  1, 10)),
                new Book("Functional Programming Patterns in Scala and Clojure", new LocalDate(2013, 11,  2)),
                new Book("Scala Cookbook",                                       new LocalDate(2013,  8, 23)),
                new Book("Testing in Scala",                                     new LocalDate(2013,  2, 13))
            }},
            { "java", new Book[ ] {
                new Book("Effective Java: Second Edition",   new LocalDate(2008, 5,  8)),
                new Book("Java Concurrency in Practice",     new LocalDate(2006, 5,  9)),
                new Book("The Well-Grounded Java Developer", new LocalDate(2012, 7, 21))
            }},
            { "LOLCODE", new Book[ ] { }}
        });
    }

    private final BookDAO    mockedDAO;
    private final String     bookListName;
    private final List<Book> bookList;

    public BookResourceTest(final String bookListName, final Book [ ] bookList) {
        this.bookListName = bookListName;
        this.bookList     = Arrays.asList(bookList);

        this.mockedDAO = mock(BookDAO.class);
        registerResourceUnderTest(new BookResource(mockedDAO));
    }

    private final Form createBookForm(final Book book) {
        final Form bookForm = new Form();

        bookForm.param("name", book.getName());
        bookForm.param("date", book.getDate().toString());
        bookForm.param("description", book.getDescription());
        bookForm.param("picture", Base64.encodeBase64URLSafeString(book.getPicture()));

        return bookForm;
    }

    @Test
    public void book_resource_is_able_to_search_by_name( ) {
        when(mockedDAO.findByName(bookListName)).thenReturn(bookList);

        final Response response = jerseyTest.target("books").queryParam(
            "search", bookListName
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(OK_CODE);
        assertThat(response.readEntity(new GenericType<List<Book>>() { }))
            .containsExactlyElementsOf(bookList);
    }

    @Test
    public void book_resource_returns_all_books_when_searching_with_empty_name( ) {
        when(mockedDAO.findByName("")).thenReturn(bookList);

        final Response response = jerseyTest.target("books").queryParam(
            "search", ""
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(OK_CODE);
        assertThat(response.readEntity(new GenericType<List<Book>>() { }))
            .containsExactlyElementsOf(bookList);
    }

    @Test
    public void book_resource_returns_a_server_error_code_when_dao_throws_exception_while_searching_by_name( ) {
        when(mockedDAO.findByName(bookListName)).thenThrow(new PersistenceException());

        final Response response = jerseyTest.target("books").queryParam(
            "search", bookListName
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(SERVER_ERROR_CODE);
    }

    @Test
    public void book_resource_is_able_to_insert_books( ) {
        final Builder request = jerseyTest.target("books").request(
            MediaType.APPLICATION_JSON
        );

        for (final Book book : bookList) {
            final Response response = request.post(Entity.entity(
                createBookForm(book),
                MediaType.APPLICATION_FORM_URLENCODED_TYPE
            ));

            assertThat(response.getStatus()).isEqualTo(OK_CODE);
            verify(mockedDAO).insert(book);
        }
    }

    @Test
    public void book_resource_returns_a_server_error_code_when_dao_throws_exception_while_inserting_a_book( ) {
        final Builder request = jerseyTest.target("books").request(
            MediaType.APPLICATION_JSON
        );

        for (final Book book : bookList) {
            doThrow(new PersistenceException()).when(mockedDAO).insert(book);

            final Response response = request.post(Entity.entity(
                createBookForm(book),
                MediaType.APPLICATION_FORM_URLENCODED_TYPE
            ));

            assertThat(response.getStatus()).isEqualTo(SERVER_ERROR_CODE);
        }
    }

}
