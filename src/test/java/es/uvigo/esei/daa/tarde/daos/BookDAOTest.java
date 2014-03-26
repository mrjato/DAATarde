package es.uvigo.esei.daa.tarde.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
	public static Collection<Book[]> createBooks() {
		return Arrays.asList(new Book[][] {
	        { 
	        	new Book("Don Quijote de la Mancha"),
	        	new Book("El Código da Vinci"),
	        	new Book("1984")
	        },
	        {
	        	new Book("Effective Java: Second Edition"),
	        	new Book("Java Concurrency in Practice"),
	        	new Book("The Well-Grounded Java Developer")
	        }
		});
	}
	
	private BookDAO dao;
	private final Book one;
	private final Book two;
	private final Book three;
	
	public BookDAOTest(Book one, Book two, Book three) {
		this.one = one;
		this.two = two;
		this.three = three;
	}

	private void saveBook(Book b) {
		if (b.getId() == null)
			entityManager.persist(b);
		else
			entityManager.merge(b);
	}
	
	@Before
	public void createBookDAO() {
		dao = new BookDAO(emFactory);
	}

	@Before
	public void insertBooks() {
		entityManager.getTransaction().begin();
		saveBook(one);
		saveBook(two);
		saveBook(three);
		entityManager.getTransaction().commit();
	}

	@Test
	public void book_dao_can_find_books_by_exact_title() {
		final List<Book> oneFound = dao.findByTitle(one.getName());
		assertThat(oneFound).contains(one);
		
		final List<Book> twoFound = dao.findByTitle(two.getName());
		assertThat(twoFound).contains(two);

		final List<Book> threeFound = dao.findByTitle(three.getName());
		assertThat(threeFound).contains(three);
	}
	
	@Test
	public void book_dao_should_return_all_books_when_searching_with_empty_title( ) {
		final List<Book> empty = dao.findByTitle("");
		assertThat(empty).containsOnly(one, two, three);
	}

}
