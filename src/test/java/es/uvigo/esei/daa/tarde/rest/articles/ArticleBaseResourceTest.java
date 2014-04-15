package es.uvigo.esei.daa.tarde.rest.articles;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import java.lang.reflect.ParameterizedType;

import org.junit.After;

import es.uvigo.esei.daa.tarde.daos.articles.GenericArticleDAO;
import es.uvigo.esei.daa.tarde.entities.articles.Article;
import es.uvigo.esei.daa.tarde.rest.BaseResourceTest;

public abstract class ArticleBaseResourceTest<T extends Article, S extends GenericArticleDAO<T>> extends BaseResourceTest {

    protected final S mockedDAO = mock(getGenericDAOClass());

    @After
    @SuppressWarnings("unchecked")
    public void resetMock( ) {
        reset(mockedDAO);
    }

    @SuppressWarnings("unchecked")
    private final Class<S> getGenericDAOClass( ) {
        return (Class<S>) (
            (ParameterizedType) getClass().getGenericSuperclass()
        ).getActualTypeArguments()[1];
    }
}
