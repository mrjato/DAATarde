package es.uvigo.esei.daa.tarde.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.DatabaseTest;
import es.uvigo.esei.daa.tarde.entities.Book;

@RunWith(Parameterized.class)
public class BookDAOTest extends DatabaseTest {

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

    private BookDAO    dao;
    
    private final Book one;
    private final Book two;
    private final Book three;

    public BookDAOTest(final Book one, final Book two, final Book three) {
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
    public void createBookDAO( ) {
        dao = new BookDAO(emFactory);
    }

    @Before
    public void insertBooks( ) {
        entityManager.getTransaction().begin();
        saveBook(one);
        saveBook(two);
        saveBook(three);
        entityManager.getTransaction().commit();
    }

    @Test
    public void book_dao_can_find_books_by_exact_title( ) {
        final List<Book> oneFound = dao.findByName(one.getName());
        assertThat(oneFound).contains(one);

        final List<Book> twoFound = dao.findByName(two.getName());
        assertThat(twoFound).contains(two);

        final List<Book> threeFound = dao.findByName(three.getName());
        assertThat(threeFound).contains(three);
    }

    @Test public void book_dao_can_find_books_by_approximate_title( ) {
        final String wordOne = one.getName().split("\\s+")[0];
        final List<Book> oneFound = dao.findByName(wordOne);
        assertThat(oneFound).contains(one);
        
        final String wordTwo = two.getName().split("\\s+")[0];
        final List<Book> twoFound = dao.findByName(wordTwo);
        assertThat(twoFound).contains(two);
        
        final String wortThree = three.getName().split("\\s+")[0];
        final List<Book> threeFound = dao.findByName(wortThree);
        assertThat(threeFound).contains(three);
    }
    
    @Test
    public void book_dao_should_return_all_books_when_searching_with_empty_title( ) {
        final List<Book> empty = dao.findByName("");
        assertThat(empty).containsOnly(one, two, three);
    }

}
