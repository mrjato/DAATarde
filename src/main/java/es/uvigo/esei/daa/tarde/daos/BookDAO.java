package es.uvigo.esei.daa.tarde.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.common.annotations.VisibleForTesting;

import es.uvigo.esei.daa.tarde.PersistenceListener;
import es.uvigo.esei.daa.tarde.entities.Book;

public class BookDAO {

	private final EntityManagerFactory emFactory;

	public BookDAO() {
		emFactory = PersistenceListener.getEntityManagerFactory();
	}

	@VisibleForTesting
	BookDAO(final EntityManagerFactory emFactory) {
		this.emFactory = emFactory;
	}

	public List<Book> findByTitle(String name) {
		final EntityManager manager = emFactory.createEntityManager();
		
		try {
			
			final List<Book> list = manager.createQuery(
			    "SELECT b FROM Book b WHERE b.name LIKE :name",
				Book.class
		    ).setParameter("name", "%" + name + "%").getResultList();

			return list;
			
		} finally {
			manager.close();
		}
	}

}
