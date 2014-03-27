package es.uvigo.esei.daa.tarde.rest;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.annotations.VisibleForTesting;

import es.uvigo.esei.daa.tarde.daos.MovieDAO;
import es.uvigo.esei.daa.tarde.entities.Movie;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource extends ArticleResource<Movie> {

    public MovieResource( ) {
        super();
    }

    @VisibleForTesting
    MovieResource(final MovieDAO dao) {
        super(dao);
    }

    protected MovieDAO createDefaultDAO( ) {
        return new MovieDAO();
    }

}
