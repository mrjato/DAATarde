package es.uvigo.esei.daa.tarde.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ArticleTest
{

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager        entityManager;

    @BeforeClass
    public static void createFactory( )
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("testing");
    }

    @Before
    public void createEntityManager( )
    {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void closeEntityManager( )
    {
        entityManager.close();
    }

    @AfterClass
    public static void closeFactory( )
    {
        entityManagerFactory.close();
    }

    @Test
    public void testArticlesAreInsertableAndRecoverableFromDatabase( )
    {
        Article article = new Article();
        article.setTitle("Test Driven Development: By Example");
        article.setAuthor("Kent Beck");
        article.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        article.setReleaseDate(new LocalDate(2002, 11, 18));
        article.setCategory(Article.Category.BOOK);

        entityManager.getTransaction().begin();
        entityManager.persist(article);
        entityManager.getTransaction().commit();
        
        Article tdd = (Article) entityManager.find(Article.class, 1L);
        assertNotNull("Article should be recovered from Database", tdd);
        
        assertEquals("Recovered title should be equal to stored title",
            article.getTitle(), tdd.getTitle()
        );
        
        assertEquals("Recovered author should be equal to stored author",
            article.getAuthor(), tdd.getAuthor()
        );
    }

}
