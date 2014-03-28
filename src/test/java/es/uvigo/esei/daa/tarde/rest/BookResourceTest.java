package es.uvigo.esei.daa.tarde.rest;

import static es.uvigo.esei.daa.tarde.rest.JerseyTestBuilder.aJerseyTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
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
        this.mockedDAO    = mock(BookDAO.class);
        this.bookListName = bookListName;
        this.bookList     = Arrays.asList(bookList);
    }

    @Override
    protected JerseyTest createJerseyTest( ) {
        return aJerseyTest().addResource(
            new BookResource(mockedDAO)
        ).build();
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

}
