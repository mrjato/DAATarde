package es.uvigo.esei.daa.tarde.daos;

import javax.persistence.EntityManagerFactory;

import es.uvigo.esei.daa.tarde.entities.Movie;

public class MovieDAO extends ArticleDAO<Movie> {

    public MovieDAO( ) {
        super();
    }

    public MovieDAO(EntityManagerFactory emFactory) {
        super(emFactory);
    }

}
