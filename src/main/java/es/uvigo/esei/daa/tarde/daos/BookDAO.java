package es.uvigo.esei.daa.tarde.daos;

import javax.persistence.EntityManagerFactory;

import com.google.common.annotations.VisibleForTesting;

import es.uvigo.esei.daa.tarde.entities.Book;

public class BookDAO extends ArticleDAO<Book> {

    public BookDAO( ) {
        super();
    }

    @VisibleForTesting
    BookDAO(final EntityManagerFactory emFactory) {
        super(emFactory);
    }

}