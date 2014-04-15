package es.uvigo.esei.daa.tarde.daos;

import java.lang.reflect.ParameterizedType;

import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;

public abstract class GenericDAO<T> {

    protected final EntityManagerFactory emFactory;

    protected GenericDAO( ) {
        emFactory = PersistenceFactory.getEntityManagerFactory();
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
