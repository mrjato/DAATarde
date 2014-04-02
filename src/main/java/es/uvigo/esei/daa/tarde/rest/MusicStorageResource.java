package es.uvigo.esei.daa.tarde.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.common.annotations.VisibleForTesting;

import es.uvigo.esei.daa.tarde.daos.MusicStorageDAO;
import es.uvigo.esei.daa.tarde.entities.MusicStorage;

@Path("/musicstorages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MusicStorageResource extends ArticleResource<MusicStorage> {

    public MusicStorageResource( ) {
        super();
    }

    @VisibleForTesting
    MusicStorageResource(final MusicStorageDAO dao) {
        super(dao);
    }

    protected MusicStorageDAO createDefaultDAO( ) {
        return new MusicStorageDAO();
    }

}
