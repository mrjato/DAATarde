package es.uvigo.esei.daa.tarde.rest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

import java.lang.reflect.ParameterizedType;

import javax.ws.rs.core.Form;

import org.apache.commons.codec.binary.Base64;
import org.junit.After;

import es.uvigo.esei.daa.tarde.daos.ArticleDAO;
import es.uvigo.esei.daa.tarde.entities.Article;

public abstract class ArticleResourceTest<T extends Article, S extends ArticleDAO<T>> extends BaseResourceTest {

    protected final S mockedDAO = mock(getGenericDAOClass());

    @After
    @SuppressWarnings("unchecked")
    public void resetMock( ) {
        reset(mockedDAO);
    }

    protected final Form createArticleForm(final T article) {
        final Form form = new Form();

        form.param("name", article.getName());
        form.param("date", article.getDate().toString());
        form.param("description", article.getDescription());
        form.param("picture", Base64.encodeBase64String(article.getPicture()));

        return form;
    }

    @SuppressWarnings("unchecked")
    private final Class<S> getGenericDAOClass() {
        return (Class<S>) (
            (ParameterizedType) getClass().getGenericSuperclass()
        ).getActualTypeArguments()[1];
    }

}
