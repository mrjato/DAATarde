package es.uvigo.esei.daa.tarde.rest;

import static es.uvigo.esei.daa.tarde.rest.JerseyTestBuilder.aJerseyTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import es.uvigo.esei.daa.tarde.daos.BookDAO;
import es.uvigo.esei.daa.tarde.entities.Book;

public class BookResourceTest extends BaseResourceTest {
    
    private BookDAO   mockedBookDAO;
    private WebTarget bookResourceTarget;
    
    @Override
    protected JerseyTest createJerseyTest( ) {
        mockedBookDAO = mock(BookDAO.class);
        
        return aJerseyTest().addResource(
            new BookResource(mockedBookDAO)
        ).build();
    }
    
    @Before
    public void createWebTarget( ) {
        bookResourceTarget = jerseyTest.target("books");
    }
    
    @Test
    public void book_resource_is_able_to_search_by_title( ) {
        populateMockedDAOWithBasicSearches();
        
        final Response resOne = bookResourceTarget.queryParam(
            "search", "haskell"
        ).request().get();
        
        assertThat(resOne.getStatus()).isEqualTo(OK_CODE);
        assertThat(resOne.readEntity(new GenericType<List<Book>>() { }))
            .containsExactlyElementsOf(mockedBookDAO.findByName("haskell"));

        final Response resTwo = bookResourceTarget.queryParam(
            "search", "scala"
        ).request().get();
        
        assertThat(resTwo.getStatus()).isEqualTo(OK_CODE);
        assertThat(resTwo.readEntity(new GenericType<List<Book>>() { }))
            .containsExactlyElementsOf(mockedBookDAO.findByName("scala"));
        
        final Response resThree = bookResourceTarget.queryParam(
            "search", "Haskell"
        ).request().get();
        
        assertThat(resThree.getStatus()).isEqualTo(OK_CODE);
        assertThat(resThree.readEntity(new GenericType<List<Book>>() { }))
            .containsExactlyElementsOf(mockedBookDAO.findByName("LOLCODE"));
    }
    
    private final void populateMockedDAOWithBasicSearches() {
        when(mockedBookDAO.findByName("haskell"))
            .thenReturn(Arrays.asList(
                new Book("Learn You a Haskell For Great Good",          "", new LocalDate(2011, 4, 18), new Byte[ ] { 0 }),
                new Book("Real World Haskell",                          "", new LocalDate(2008, 12, 5), new Byte[ ] { 0 }),
                new Book("Beginning Haskell: A Project-Based Approach", "", new LocalDate(2014, 1, 22), new Byte[ ] { 0 })
            ));
        
        when(mockedBookDAO.findByName("scala"))
            .thenReturn(Arrays.asList(
                new Book("Programming in Scala",                                 "", new LocalDate(2011, 1, 10), new Byte[ ] { 0 }),
                new Book("Functional Programming Patterns in Scala and Clojure", "", new LocalDate(2013, 11, 2), new Byte[ ] { 0 }),
                new Book("Scala Cookbook",                                       "", new LocalDate(2013, 8, 23), new Byte[ ] { 0 }),
                new Book("Testing in Scala",                                     "", new LocalDate(2013, 2, 13), new Byte[ ] { 0 })
            ));
        
        when(mockedBookDAO.findByName("LOLCODE"))
            .thenReturn(Collections.<Book>emptyList());
    }

}
