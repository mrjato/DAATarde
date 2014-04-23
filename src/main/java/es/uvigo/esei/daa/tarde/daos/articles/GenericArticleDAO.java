package es.uvigo.esei.daa.tarde.daos.articles;

import static es.uvigo.esei.daa.tarde.daos.DatabaseSession.withTransaction;
import static es.uvigo.esei.daa.tarde.daos.DatabaseSession.withoutTransaction;

import java.util.List;

import es.uvigo.esei.daa.tarde.daos.DatabaseSession;
import es.uvigo.esei.daa.tarde.daos.GenericDAO;
import es.uvigo.esei.daa.tarde.entities.articles.Article;

public abstract class GenericArticleDAO<T extends Article> extends GenericDAO<T> {

    public List<T> findByName(final String name) {
        try (final DatabaseSession session = withoutTransaction()) {
            return session.manager.createQuery(
                "SELECT a FROM " + getEntityName() + " a "
                    + "WHERE UPPER(a.name) LIKE :name "
                    + "AND a.isVerified = true "
                    + "ORDER BY a.name ASC",
                getGenericClass()
            ).setParameter(
                "name", "%" + name.toUpperCase() + "%"
            ).getResultList();
        }
    }

    public List<Article> findLatest(int num) {
        try (final DatabaseSession session = withoutTransaction()) {
            return session.manager.createQuery(
                "SELECT a FROM Article a "
                    + "WHERE a.isVerified = true "
                    + "ORDER BY a.id DESC",
                Article.class
            ).setMaxResults(num).getResultList();
        }
    }

    public void save(final T article) {
        if (article.isPersisted()) update(article); else insert(article);
    }

    void insert(final T article) {
        try (final DatabaseSession session = withTransaction()) {
            session.manager.persist(article);
        }
    }

    void update(final T article) {
        try (final DatabaseSession session = withTransaction()) {
            session.manager.merge(article);
        }
    }

}
