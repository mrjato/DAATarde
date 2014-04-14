package es.uvigo.esei.daa.tarde.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.annotations.VisibleForTesting;

import es.uvigo.esei.daa.tarde.daos.ArticleDAO;
import es.uvigo.esei.daa.tarde.entities.Article;


@Path("/articles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArticleResource extends Resource<Article> {

    public ArticleResource( ) {
        super();
    }

    @VisibleForTesting
    ArticleResource(final ArticleDAO dao) {
        super(dao);
    }

    protected ArticleDAO createDefaultDAO( ) {
        return new ArticleDAO();
    }

}

