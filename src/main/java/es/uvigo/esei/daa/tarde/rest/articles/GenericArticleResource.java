package es.uvigo.esei.daa.tarde.rest.articles;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import es.uvigo.esei.daa.tarde.daos.articles.GenericArticleDAO;
import es.uvigo.esei.daa.tarde.entities.articles.Article;

public abstract class GenericArticleResource<T extends Article> {

    protected final GenericArticleDAO<T> dao;

    protected GenericArticleResource(final GenericArticleDAO<T> dao) {
        this.dao = dao;
    }

    @GET
    @SuppressWarnings("unchecked")
    public Response search(@QueryParam("search") final String name) {
        try {
            return Response.ok().entity(new GenericEntity<List<Article>>(
                (List<Article>) dao.findByName(name)
            ) { }).build();
        } catch (final Exception _) {
            return Response.serverError().build();
        }
    }

    @POST
    public Response insert(final T article) {
        if (article.getId() != null)
            return Response.status(Status.BAD_REQUEST).build();

        try {
            dao.save(article);
            return Response.ok(article.getId()).build();
        } catch (final PersistenceException pe) {
            pe.printStackTrace();
            return Response.serverError().build();
        }
    }

}
