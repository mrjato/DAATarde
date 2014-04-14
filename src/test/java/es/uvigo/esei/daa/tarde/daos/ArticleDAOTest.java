package es.uvigo.esei.daa.tarde.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.TestUtils;
import es.uvigo.esei.daa.tarde.entities.Article;
import es.uvigo.esei.daa.tarde.entities.Book;
import es.uvigo.esei.daa.tarde.entities.Comic;
import es.uvigo.esei.daa.tarde.entities.MusicStorage;

@RunWith(Parameterized.class)
public class ArticleDAOTest extends BaseDAOTest {

    @Parameters(name = "{index}: {0} ")
    public static final Collection<Object[ ]> createArticleData( ) {
        return Arrays.asList(new Object[ ][ ]{
            { "haskell", new Book[ ] {
                new Book("Learn You a Haskell For Great Good",          new LocalDate(2011,  4, 18)),
                new Book("Real World Haskell",                          new LocalDate(2008, 12,  5)),
                new Book("Beginning Haskell: A Project-Based Approach", new LocalDate(2014,  1, 22))
            }},
            { "scala", new MusicStorage[ ] {
                new MusicStorage("Programming in Scala",                                 new LocalDate(2011,  1, 10)),
                new MusicStorage("Functional Programming Patterns in Scala and Clojure", new LocalDate(2013, 11,  2)),
                new MusicStorage("Scala Cookbook",                                       new LocalDate(2013,  8, 23)),
                new MusicStorage("Testing in Scala",                                     new LocalDate(2013,  2, 13))
            }},
            { "java", new Comic[ ] {
                new Comic("Effective Java: Second Edition",   new LocalDate(2008, 5,  8)),
                new Comic("Java Concurrency in Practice",     new LocalDate(2006, 5,  9)),
                new Comic("The Well-Grounded Java Developer", new LocalDate(2012, 7, 21))
            }},
            { "LOLCODE", new Book[ ] { }}
        });
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private ArticleDAO dao;
    private final List<Article> articleList;

    public ArticleDAOTest(final String _, final Article [ ] articleList) {
        this.articleList = Arrays.asList(articleList);
    }

    private void unverifyArticle(final Article b) {
        entityManager.getTransaction().begin();

        final Article article = entityManager.find(Article.class, b.getId());
        article.setVerified(false);
        entityManager.merge(article);

        entityManager.getTransaction().commit();
    }

    @Before
    public void createArticleDAO( ) {
        dao = new ArticleDAO();
    }

    @Before
    public void insertArticles( ) {
        entityManager.getTransaction().begin();
        for (final Article b : articleList) {
            b.setVerified(true);
            if (b.getId() == null) entityManager.persist(b);
            else                   entityManager.merge(b);
        }
        entityManager.getTransaction().commit();
    }


    @Test
    public void article_dao_can_find_articles_by_exact_title( ) {
        for (final Article article : articleList) {
            final List<Article> found = dao.findByName(article.getName());
            assertThat(found).contains(article);
        }
    }

    @Test
    public void article_dao_can_find_articles_by_approximate_title( ) {
        for (final Article article : articleList) {
            final String word = article.getName().split("\\s+")[0];
            final List<Article> found = dao.findByName(word);
            assertThat(found).contains(article);
        }
    }

    @Test
    public void article_dao_finds_articles_ignoring_case( ) {
        for (final Article article : articleList) {
            final String word = article.getName().split("\\s+")[0];

            final List<Article> upper = dao.findByName(word.toUpperCase());
            assertThat(upper).contains(article);

            final List<Article> lower = dao.findByName(word.toLowerCase());
            assertThat(lower).contains(article);

            final List<Article> rand  = dao.findByName(TestUtils.randomizeCase(word));
            assertThat(rand).contains(article);
        }
    }

    @Test
    public void article_dao_should_return_all_articles_when_searching_with_empty_title( ) {
        final List<Article> empty = dao.findByName("");
        assertThat(empty).isEqualTo(articleList);
    }

    @Test
    public void article_dao_should_ignore_non_verified_articles_when_searching_by_name( ) {
        for (final Article article : articleList) {
            unverifyArticle(article);

            final List<Article> found = dao.findByName(article.getName());
            assertThat(found).doesNotContain(article);
        }
    }  
}
