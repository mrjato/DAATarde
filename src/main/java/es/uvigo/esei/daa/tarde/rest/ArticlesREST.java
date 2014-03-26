package es.uvigo.esei.daa.tarde.rest;

import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uvigo.esei.daa.tarde.daos.ArticleDAO;
import es.uvigo.esei.daa.tarde.entities.Article;

public abstract class ArticlesREST<T extends Article> {
    
    protected final ArticleDAO<T> dao;
    
    public ArticlesREST( ) {
        dao = createDAO();
    }
    
    protected abstract ArticleDAO<T> createDAO();
    
    @GET
    public Response search(@QueryParam("search") final String name) {
        return Response.ok(dao.findByName(name), MediaType.APPLICATION_JSON).build();
    }

}
