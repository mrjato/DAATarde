package es.uvigo.esei.daa.tarde.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.persistence.PersistenceException;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.TestUtils;
import es.uvigo.esei.daa.tarde.entities.Book;

@RunWith(Parameterized.class)
public class BookDAOTest extends BaseDAOTest {

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[ ]> createBookData( ) {
        return Arrays.asList(new Object[ ][ ] {
            { "asoiaf", new Book[ ] {
                new Book("A Game of Thrones", new LocalDate(1996,  8,  6)),
                new Book("A Clash of Kings",  new LocalDate(1998, 11, 16)),
                new Book("A Storm of Words",  new LocalDate(2000,  8,  8)),
                new Book("A Fest for Crows",  new LocalDate(2005, 10, 17))
            }},
            { "pkdick", new Book[ ] {
                new Book("The Man in the High Castle",           new LocalDate(1962, 1, 1)),
                new Book("Do Androids Dream of Electric Sheep?", new LocalDate(1968, 1, 1)),
                new Book("Ubik",                                 new LocalDate(1969, 1, 1)),
                new Book("A Scanner Darkly",                     new LocalDate(1977, 1, 1)),
                new Book("VALIS",                                new LocalDate(1981, 1, 1)),
                new Book("The Divine Invasion",                  new LocalDate(1981, 1, 1)),
                new Book("The Owl in Daylight",                  new LocalDate(1982, 1, 1))
            }},
            { "odyssey", new Book[ ] {
                new Book("2001: A Space Odyssey",   new LocalDate(1968, 1, 1)),
                new Book("2010: Odyssey Two",       new LocalDate(1982, 1, 1)),
                new Book("2061: Odyssey Three",     new LocalDate(1987, 1, 1)),
                new Book("3001: The Final Odyssey", new LocalDate(1997, 1, 1))
            }}
        });
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private BookDAO dao;
    private final List<Book> bookList;

    public BookDAOTest(final String _, final Book [ ] bookList) {
        this.bookList = Arrays.asList(bookList);
    }

    private void unverifyBook(final Book b) {
        entityManager.getTransaction().begin();

        b.setVerified(false);
        entityManager.merge(b);

        entityManager.getTransaction().commit();
    }

    @Before
    public void createBookDAO( ) {
        dao = new BookDAO();
    }

    @Before
    public void insertBooks( ) {
        entityManager.getTransaction().begin();
        for (final Book b : bookList) {
            b.setVerified(true);
            if (b.getId() == null) entityManager.persist(b);
            else                   entityManager.merge(b);
        }
        entityManager.getTransaction().commit();
    }


    @Test
    public void book_dao_can_find_books_by_exact_title( ) {
        for (final Book book : bookList) {
            final List<Book> found = dao.findByName(book.getName());
            assertThat(found).contains(book);
        }
    }

    @Test
    public void book_dao_can_find_books_by_approximate_title( ) {
        for (final Book book : bookList) {
            final String word = book.getName().split("\\s+")[0];
            final List<Book> found = dao.findByName(word);
            assertThat(found).contains(book);
        }
    }

    @Test
    public void book_dao_finds_books_ignoring_case( ) {
        for (final Book book : bookList) {
            final String word = book.getName().split("\\s+")[0];

            final List<Book> upper = dao.findByName(word.toUpperCase());
            assertThat(upper).contains(book);

            final List<Book> lower = dao.findByName(word.toLowerCase());
            assertThat(lower).contains(book);

            final List<Book> rand  = dao.findByName(TestUtils.randomizeCase(word));
            assertThat(rand).contains(book);
        }
    }

    @Test
    public void book_dao_should_return_all_books_when_searching_with_empty_title( ) {
        final List<Book> empty = dao.findByName("");
        assertThat(empty).isEqualTo(bookList);
    }

    @Test
    public void book_dao_should_ignore_non_verified_books_when_searching_by_name( ) {
        for (final Book book : bookList) {
            unverifyBook(book);

            final List<Book> found = dao.findByName(book.getName());
            assertThat(found).doesNotContain(book);
        }
    }

    @Test
    public void book_dao_can_insert_books( ) {
        for (final Book book : bookList) {
            final Book inserted = new Book(book.getName(), book.getDate());
            dao.insert(inserted);

            final Long id = inserted.getId();
            assertThat(id).isNotNull();

            final Book found = entityManager.find(Book.class, id);
            assertThat(found).isEqualTo(inserted);
        }
    }

    @Test
    public void book_dao_should_throw_an_exception_when_inserting_an_already_inserted_book( ) {
        thrown.expect(PersistenceException.class);

        final Random random = new Random();
        final Book book = bookList.get(random.nextInt(bookList.size()));

        dao.insert(book);
    }

}
