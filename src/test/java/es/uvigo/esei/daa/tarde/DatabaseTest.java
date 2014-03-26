package es.uvigo.esei.daa.tarde;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

public abstract class DatabaseTest {

    protected static EntityManagerFactory emFactory;
    protected static EntityManager        entityManager;

    @Before
    public void createEntityManager( ) {
        emFactory = Persistence.createEntityManagerFactory("testing");
        entityManager = emFactory.createEntityManager();
    }

    @After
    public void closeEntityManager( ) {
        entityManager.close();
        emFactory.close();
    }

}
