package es.uvigo.esei.daa.tarde.daos;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import es.uvigo.esei.daa.tarde.PersistenceListener;
import es.uvigo.esei.daa.tarde.entities.Article;

public abstract class ArticleDAO<T extends Article> {

    protected final EntityManagerFactory emFactory;

    public ArticleDAO( ) {
        emFactory = PersistenceListener.getEntityManagerFactory();
    }
    
    public List<T> findByName(final String name) {
        final EntityManager manager = emFactory.createEntityManager();

        try {
            
            return manager.createQuery(
                "SELECT a FROM " + getEntityName() + " a WHERE UPPER(a.name) LIKE :name",
                getGenericClass()
            ).setParameter("name", "%" + name.toUpperCase() + "%").getResultList();
            
        } finally {
            if (manager.isOpen()) manager.close();
        }
    }
    
    protected String getEntityName( ) {
        final Class<T> clazz = getGenericClass();
        final String name = clazz.getAnnotation(Entity.class).name();
        
        if (name.isEmpty()) return clazz.getSimpleName();
        else return name;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getGenericClass() {
        return (Class<T>) (
            (ParameterizedType) this.getClass().getGenericSuperclass()
        ).getActualTypeArguments()[0];
    }
}
