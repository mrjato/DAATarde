package es.uvigo.esei.daa.tarde.rest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;

import es.uvigo.esei.daa.tarde.PersistenceListener;

public abstract class BaseRESTTest extends JerseyTest {

    protected static EntityManagerFactory emFactory;
    protected static EntityManager        entityManager;

    @Before
    public void createEntityManager( ) {
        PersistenceListener.setEmFactory("testing");

        emFactory = PersistenceListener.getEntityManagerFactory();
        entityManager = emFactory.createEntityManager();
    }

    @After
    public void closeEntityManager( ) {
        entityManager.close();
        emFactory.close();
    }


}
