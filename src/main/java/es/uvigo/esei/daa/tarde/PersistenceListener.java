package es.uvigo.esei.daa.tarde;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PersistenceListener implements ServletContextListener {

    private static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory createEntityManagerFactory(
        final String persistenceUnitName
    ) {
        entityManagerFactory = Persistence.createEntityManagerFactory(
            persistenceUnitName
        );

        return entityManagerFactory;
    }

    public static EntityManagerFactory getEntityManagerFactory( ) {
        if (entityManagerFactory == null) {
            throw new IllegalStateException(
                "Context has not yet been initialized."
            );
        }

        return entityManagerFactory;
    }

    @Override
    public void contextInitialized(final ServletContextEvent _) {
        createEntityManagerFactory("default");
    }

    @Override
    public void contextDestroyed(final ServletContextEvent _) {
        entityManagerFactory.close();
    }

}
