package es.uvigo.esei.daa.tarde.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.Before;

import es.uvigo.esei.daa.tarde.PersistenceListener;

public abstract class BaseDAOTest {

    protected static EntityManagerFactory emFactory;
    protected static EntityManager        entityManager;

    @Before
    public void createEntityManager( ) {
        PersistenceListener.createEntityManagerFactory("testing");

        emFactory = PersistenceListener.getEntityManagerFactory();
        entityManager = emFactory.createEntityManager();
    }

    @After
    public void closeEntityManager( ) {
        entityManager.close();
        emFactory.close();
    }

}
