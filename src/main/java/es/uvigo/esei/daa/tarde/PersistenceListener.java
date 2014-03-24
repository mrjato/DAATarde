package es.uvigo.esei.daa.tarde;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PersistenceListener implements ServletContextListener {

    private static EntityManagerFactory emFactory;

    @Override
    public void contextInitialized(ServletContextEvent _) {
        emFactory = Persistence.createEntityManagerFactory("default");
    }

    @Override
    public void contextDestroyed(ServletContextEvent _) {
        emFactory.close();
    }

    public static EntityManagerFactory getEntityManagerFactory( ) {
        if (emFactory == null) {
            throw new IllegalStateException(
                "Context has not yet been initialized."
            );
        }

        return emFactory;
    }

}
