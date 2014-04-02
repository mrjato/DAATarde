package es.uvigo.esei.daa.tarde.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.annotations.VisibleForTesting;

import es.uvigo.esei.daa.tarde.daos.BookDAO;
import es.uvigo.esei.daa.tarde.entities.Book;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource extends ArticleResource<Book> {

    public BookResource( ) {
        super();
    }

    @VisibleForTesting
    BookResource(final BookDAO dao) {
        super(dao);
    }

    protected BookDAO createDefaultDAO( ) {
        return new BookDAO();
    }

}
