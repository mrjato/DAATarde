package es.uvigo.esei.daa.tarde;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.Before;

public abstract class DatabaseTest {

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
