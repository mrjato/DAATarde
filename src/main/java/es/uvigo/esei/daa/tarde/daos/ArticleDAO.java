package es.uvigo.esei.daa.tarde.daos;

import java.util.List;

import javax.persistence.EntityManager;

import es.uvigo.esei.daa.tarde.entities.Article;

public abstract class ArticleDAO<T extends Article> extends GenericDAO<T> {

    public List<T> findByName(final String name) {
        final EntityManager manager = emFactory.createEntityManager();
        try {
            return manager.createQuery(
                "SELECT a FROM " + getEntityName() + " a WHERE UPPER(a.name) LIKE :name",
                getGenericClass()
            ).setParameter("name", "%" + name.toUpperCase() + "%").getResultList();
        } finally {
            manager.close();
        }
    }

}
