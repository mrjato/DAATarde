package es.uvigo.esei.daa.tarde.daos;


import java.util.List;

import javax.persistence.EntityManager;

import es.uvigo.esei.daa.tarde.entities.Article;

public class ArticleDAO extends DAO<Article> {
    
    @Override
    public List<Article> findByName(final String name) {
        final EntityManager manager = emFactory.createEntityManager();
        try {
            return manager.createQuery(
                "SELECT a FROM "+getEntityName()+" a "
                        + "WHERE UPPER(a.name) LIKE :name "
                        + "AND a.isVerified = true",
                getGenericClass()
            ).setParameter("name", "%" + name.toUpperCase() + "%").getResultList();
        } finally {
            if (manager.isOpen()) manager.close();
        }
        
    }
}
