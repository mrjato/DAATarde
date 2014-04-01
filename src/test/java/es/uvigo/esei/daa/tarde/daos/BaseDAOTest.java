package es.uvigo.esei.daa.tarde.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.Before;

public abstract class BaseDAOTest {

    protected static EntityManagerFactory emFactory;
    protected static EntityManager        entityManager;

    @Before
    public void createEntityManager( ) {
        PersistenceFactory.createEntityManagerFactory("testing");

        emFactory = PersistenceFactory.getEntityManagerFactory();
        entityManager = emFactory.createEntityManager();
    }

    @After
    public void closeEntityManager( ) {
        entityManager.close();
        emFactory.close();
    }

}
