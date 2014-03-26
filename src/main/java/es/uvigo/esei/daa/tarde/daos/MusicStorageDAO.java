package es.uvigo.esei.daa.tarde.daos;

import javax.persistence.EntityManagerFactory;

import es.uvigo.esei.daa.tarde.entities.MusicStorage;

public class MusicStorageDAO extends ArticleDAO<MusicStorage> {

    public MusicStorageDAO( ) {
        super();
    }

    public MusicStorageDAO(EntityManagerFactory emFactory) {
        super(emFactory);
    }

}
