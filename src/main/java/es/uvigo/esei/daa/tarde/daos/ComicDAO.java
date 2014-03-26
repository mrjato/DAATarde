package es.uvigo.esei.daa.tarde.daos;

import javax.persistence.EntityManagerFactory;

import com.google.common.annotations.VisibleForTesting;

import es.uvigo.esei.daa.tarde.entities.Comic;

public class ComicDAO extends ArticleDAO<Comic> {

    public ComicDAO( ) {
        super();
    }

    @VisibleForTesting
    ComicDAO(EntityManagerFactory emFactory) {
        super(emFactory);
    }

}
