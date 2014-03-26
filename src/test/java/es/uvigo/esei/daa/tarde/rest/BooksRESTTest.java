package es.uvigo.esei.daa.tarde.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.entities.Book;

@RunWith(Parameterized.class)
public class BooksRESTTest extends BaseRESTTest {
    
    @Parameters
    public static Collection<Book[ ]> createBooks( ) {
        return Arrays.asList(new Book[ ][ ] {
            {
                new Book("Don Quijote de la Mancha", "", new LocalDate(), new Byte[ ] { 0 }),
                new Book("El Código da Vinci",       "", new LocalDate(), new Byte[ ] { 0 }),
                new Book("1984",                     "", new LocalDate(), new Byte[ ] { 0 })
            },
            {
                new Book("Effective Java: Second Edition",   "", new LocalDate(2008, 5, 8),  new Byte[ ] { 0 }),
                new Book("Java Concurrency in Practice",     "", new LocalDate(2006, 5, 9),  new Byte[ ] { 0 }),
                new Book("The Well-Grounded Java Developer", "", new LocalDate(2012, 7, 21), new Byte[ ] { 0 })
            }
        });
    }
    
    private final Book one;
    private final Book two;
    private final Book three;
    
    public BooksRESTTest(final Book one, final Book two, final Book three) {
        this.one   = one;
        this.two   = two;
        this.three = three;
    }
    
    private void saveBook(final Book book) {
        if (book.getId() == null)
            entityManager.persist(book);
        else
            entityManager.merge(book);
    }
    
    @Before
    public void insertBooks( ) {
        entityManager.getTransaction().begin();
        saveBook(one);
        saveBook(two);
        saveBook(three);
        entityManager.getTransaction().commit();
    }
    
    @Override
    protected Application configure( ) {
        return new ResourceConfig(BooksREST.class)
            .register(JacksonJsonProvider.class)
            .property(
                "com.sun.jersey.api.json.POJOMappingFeature",
                Boolean.TRUE
            );
    }

    @Override
    protected void configureClient(ClientConfig config) {
        super.configureClient(config);

        config.register(JacksonJsonProvider.class);
        config.property(
            "com.sun.jersey.api.json.POJOMappingFeature",
            Boolean.TRUE
        );
    }
    
    @Test
    public void books_rest_is_able_to_search_by_title( ) {
        final Response response = target("books").queryParam("search", one.getName()).request().get();
        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());

        final List<Book> books = response.readEntity(new GenericType<List<Book>>() { });
        assertThat(books).contains(one);
    }

}
