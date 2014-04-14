package es.uvigo.esei.daa.tarde.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.daos.ArticleDAO;
import es.uvigo.esei.daa.tarde.entities.Article;
import es.uvigo.esei.daa.tarde.entities.Book;
import es.uvigo.esei.daa.tarde.entities.Comic;
import es.uvigo.esei.daa.tarde.entities.MusicStorage;

@RunWith(Parameterized.class)
public class ArticleResourceTest extends ResourceTest<Article, ArticleDAO> {

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

    private final String     articleListName;
    private final List<Article> articleList;

    public ArticleResourceTest(final String articleListName, final Article [ ] articleList) {
        this.articleListName = articleListName;
        this.articleList     = Arrays.asList(articleList);
        registerResourceUnderTest(new ArticleResource(mockedDAO));
    }

    @Test
    public void article_resource_is_able_to_search_by_name( ) {
        when(mockedDAO.findByName(articleListName)).thenReturn(articleList);

        final Response response = jerseyTest.target("articles").queryParam(
            "search", articleListName
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(OK_CODE);
        assertThat(response.readEntity(
            new GenericType<List<Article>>() { }
        )).containsExactlyElementsOf(articleList);
    }

    @Test
    public void article_resource_returns_all_articles_when_searching_with_empty_name( ) {
        when(mockedDAO.findByName("")).thenReturn(articleList);

        final Response response = jerseyTest.target("articles").queryParam(
            "search", ""
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(OK_CODE);
        assertThat(response.readEntity(
            new GenericType<List<Article>>() { }
        )).containsExactlyElementsOf(articleList);
    }

    @Test
    public void article_resource_returns_a_server_error_code_when_dao_throws_exception_while_searching_by_name( ) {
        when(mockedDAO.findByName(articleListName)).thenThrow(new PersistenceException());

        final Response response = jerseyTest.target("articles").queryParam(
            "search", articleListName
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(SERVER_ERROR_CODE);
    }
}
