package es.uvigo.esei.daa.tarde.daos.articles;

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
import es.uvigo.esei.daa.tarde.daos.BaseDAOTest;
import es.uvigo.esei.daa.tarde.entities.articles.Article;
import es.uvigo.esei.daa.tarde.entities.articles.Comic;
import es.uvigo.esei.daa.tarde.entities.articles.Movie;
import es.uvigo.esei.daa.tarde.entities.articles.MusicStorage;

@RunWith(Parameterized.class)
public class ArticleDAOTest extends BaseDAOTest {

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[ ]> createArticleData( ) {
        return Arrays.asList(new Object[ ][ ] {
            { "lonewolfandcub", new Article[ ] {
                new Comic("Kozure Ōkami",                    new LocalDate(1970,  1,  1)),
                new Movie("Kowokashi udekashi tsukamatsuru", new LocalDate(1972,  4,  1)),
                new Movie("Sanzu no kawa no ubaguruma",      new LocalDate(1972,  6,  1)),
                new Movie("Shogun Assassin",                 new LocalDate(1980, 11, 11)),
                new MusicStorage("Liquid Swords",            new LocalDate(1995, 11,  7))
            }}
        });
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private ArticleDAO dao;
    private final List<Article> articleList;

    public ArticleDAOTest(final String _, final Article [ ] articleList) {
        this.articleList = Arrays.asList(articleList);
    }

    private void unverifyArticle(final Article a) {
        entityManager.getTransaction().begin();

        final Article article = entityManager.find(Article.class, a.getId());
        article.setVerified(false);
        entityManager.merge(article);

        entityManager.getTransaction().commit();
    }

    @Before
    public void createArticleDAO( ) {
        dao = new ArticleDAO( );
    }

    @Before
    public void insertArticles( ) {
        entityManager.getTransaction().begin();
        for (final Article a : articleList) {
            a.setVerified(true);
            if (a.getId() == null) entityManager.persist(a);
            else                   entityManager.merge(a);
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

            final List<Article> rand = dao.findByName(TestUtils.randomizeCase(word));
            assertThat(rand).contains(article);
        }
    }

    @Test
    public void article_dao_should_return_all_articles_when_searching_with_empty_title( ) {
        final List<Article> empty = dao.findByName("");
        assertThat(empty).containsAll(articleList);
    }

    @Test
    public void article_dao_should_ignore_non_verified_articles_when_searching_by_name( ) {
        for (final Article article : articleList) {
            unverifyArticle(article);

            final List<Article> found = dao.findByName(article.getName());
            assertThat(found).doesNotContain(article);
        }
    }

    @Test
    public void article_dao_should_throw_an_exception_if_trying_to_insert_a_new_article( ) {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage(
            "Cannot insert a bare Article, use a subtype and its DAO."
        );

        dao.insert(articleList.get(0));
    }

    @Test
    public void article_dao_should_throw_an_exception_if_trying_to_update_an_article( ) {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage(
            "Cannot update a bare Article, use a subtype and its DAO."
        );

        dao.update(articleList.get(0));
    }

    @Test
    public void article_dao_can_find_ten_latest_articles( ) {
        final List<Article> found = dao.findLatest(3);
        assertThat(found.size()).isEqualTo(3);
        assertThat(found).doesNotContain(articleList.get(0), articleList.get(1));
    }

}
