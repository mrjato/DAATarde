package es.uvigo.esei.daa.tarde.rest;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

import java.lang.reflect.ParameterizedType;

import javax.persistence.PersistenceException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.joda.time.LocalDate;

import es.uvigo.esei.daa.tarde.daos.ArticleDAO;
import es.uvigo.esei.daa.tarde.entities.Article;

public abstract class ArticleResource<T extends Article> {

    protected final ArticleDAO<T> dao;

    protected ArticleResource( ) {
        dao = createDefaultDAO();
    }

    protected ArticleResource(final ArticleDAO<T> dao) {
        this.dao = dao;
    }

    protected abstract ArticleDAO<T> createDefaultDAO();

    @GET
    public Response search(@QueryParam("search") final String name) {
        try {
            return Response.ok(
                dao.findByName(name), MediaType.APPLICATION_JSON
            ).build();
        } catch (final Exception _) {
            return Response.serverError().build();
        }
    }

    @POST
    public Response insert(
        @FormParam("name")        final String name,
        @FormParam("date")        final String date,
        @FormParam("description") final String description,
        @FormParam("picture")     final String picture
    ) {
        try {

            final T article = articleFactory(
                name, new LocalDate(date), description, decodeBase64(picture)
            );

            dao.insert(article);
            return Response.ok(article.getId()).build();

        } catch (final IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            return Response.status(Status.BAD_REQUEST).build();
        } catch (final PersistenceException pe) {
            pe.printStackTrace();
            return Response.serverError().build();
        }
    }

    @SuppressWarnings("unchecked")
    private final Class<T> getGenericClass( ) {
        return (Class<T>) (
            (ParameterizedType) getClass().getGenericSuperclass()
        ).getActualTypeArguments()[0];
    }

    protected T articleFactory(
        final String    name,
        final LocalDate date,
        final String    description,
        final byte[ ]   picture
    ) {
        try {
            return getGenericClass().getDeclaredConstructor(
                String.class, String.class, LocalDate.class, byte[ ].class
            ).newInstance(name, description, date, picture);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

}
