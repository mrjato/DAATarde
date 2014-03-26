package es.uvigo.esei.daa.tarde.rest;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.uvigo.esei.daa.tarde.daos.BookDAO;
import es.uvigo.esei.daa.tarde.entities.Book;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BooksREST extends ArticlesREST<Book> {

    protected BookDAO createDAO( ) {
        return new BookDAO();
    }
    
}
