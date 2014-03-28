package es.uvigo.esei.daa.tarde.daos;

import java.lang.reflect.ParameterizedType;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;

import es.uvigo.esei.daa.tarde.PersistenceListener;

public abstract class GenericDAO<T> {

    protected final EntityManagerFactory emFactory;

    protected GenericDAO( ) {
        emFactory = PersistenceListener.getEntityManagerFactory();
    }

    protected String getEntityName( ) {
        final Class<T> clazz = getGenericClass();
        final String name = clazz.getAnnotation(Entity.class).name();

        if (name.isEmpty()) return clazz.getSimpleName();
        else return name;
    }

    @SuppressWarnings("unchecked")
    protected final Class<T> getGenericClass() {
        return (Class<T>) (
            (ParameterizedType) getClass().getGenericSuperclass()
        ).getActualTypeArguments()[0];
    }

}
