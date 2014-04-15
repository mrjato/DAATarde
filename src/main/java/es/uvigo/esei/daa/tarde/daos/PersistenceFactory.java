package es.uvigo.esei.daa.tarde.daos;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class PersistenceFactory  {

    private static EntityManagerFactory factory;

    public static EntityManagerFactory createEntityManagerFactory(
        final String persistenceUnitName
    ) {
        factory = Persistence.createEntityManagerFactory(persistenceUnitName);
        return factory;
    }

    public static EntityManagerFactory getEntityManagerFactory( ) {
        if (factory == null) throw new IllegalStateException(
            "EntityManagerFactory has not yet been created."
        );

        return factory;
    }

}
