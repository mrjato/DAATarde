package es.uvigo.esei.daa.tarde.rest;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.annotations.VisibleForTesting;

import es.uvigo.esei.daa.tarde.daos.ComicDAO;
import es.uvigo.esei.daa.tarde.entities.Comic;

@Path("/comics")
@Produces(MediaType.APPLICATION_JSON)
public class ComicResource extends ArticleResource<Comic> {

    public ComicResource( ) {
        super();
    }

    @VisibleForTesting
    ComicResource(final ComicDAO dao) {
        super(dao);
    }

    protected ComicDAO createDefaultDAO( ) {
        return new ComicDAO();
    }

}
