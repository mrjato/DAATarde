package es.uvigo.esei.daa.tarde.daos.articles;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import es.uvigo.esei.daa.tarde.daos.GenericDAO;
import es.uvigo.esei.daa.tarde.entities.articles.Article;

public abstract class GenericArticleDAO<T extends Article> extends GenericDAO<T> {

    public List<T> findByName(final String name) {
        final EntityManager manager = emFactory.createEntityManager();
        try {

            return manager.createQuery(
                "SELECT a FROM " + getEntityName() + " a "
                    + "WHERE UPPER(a.name) LIKE :name "
                    + "AND a.isVerified = true "
                    + "ORDER BY a.name ASC",
                getGenericClass()
            ).setParameter("name", "%" + name.toUpperCase() + "%").getResultList();

        } finally {
            if (manager.isOpen()) manager.close();
        }
    }

    public void save(final T article) {
        if (article.getId() == null) insert(article);
        else                         update(article);
    }

    void insert(final T article) {
        final EntityManager manager = emFactory.createEntityManager();
        final EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.persist(article);
            transaction.commit();
        } finally {
            if (transaction.isActive()) transaction.rollback();
            if (manager.isOpen())       manager.close();
        }
    }

    void update(final T article) {
        final EntityManager manager = emFactory.createEntityManager();
        final EntityTransaction transaction = manager.getTransaction();

        try {
            transaction.begin();
            manager.merge(article);
            transaction.commit();
        } finally {
            if (transaction.isActive()) transaction.rollback();
            if (manager.isOpen())       manager.close();
        }
    }

}
