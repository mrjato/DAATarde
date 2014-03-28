package es.uvigo.esei.daa.tarde.rest;

import static es.uvigo.esei.daa.tarde.rest.JerseyTestBuilder.aJerseyTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import es.uvigo.esei.daa.tarde.daos.MusicStorageDAO;
import es.uvigo.esei.daa.tarde.entities.MusicStorage;

public class MusicStorageResourceTest extends BaseResourceTest {
    
    private MusicStorageDAO mockedMusicStorageDAO;
    private WebTarget       musicStorageResourceTarget;
    
    @Override
    protected JerseyTest createJerseyTest( ) {
        mockedMusicStorageDAO = mock(MusicStorageDAO.class);
        
        return aJerseyTest().addResource(
            new MusicStorageResource(mockedMusicStorageDAO)
        ).build();
    }
    
    @Before
    public void createWebTarget( ) {
        musicStorageResourceTarget = jerseyTest.target("musicstorages");
    }
    
    @Test
    public void music_storage_resource_is_able_to_search_by_title( ) {
        populateMockedDAOWithBasicSearches();
        
        final Response resOne = musicStorageResourceTarget.queryParam(
            "search", "love"
        ).request().get();
        
        assertThat(resOne.getStatus()).isEqualTo(OK_CODE);
        assertThat(resOne.readEntity(new GenericType<List<MusicStorage>>() { }))
            .containsExactlyElementsOf(mockedMusicStorageDAO.findByName("love"));

        final Response resTwo = musicStorageResourceTarget.queryParam(
            "search", "zeppelin"
        ).request().get();
        
        assertThat(resTwo.getStatus()).isEqualTo(OK_CODE);
        assertThat(resTwo.readEntity(new GenericType<List<MusicStorage>>() { }))
            .containsExactlyElementsOf(mockedMusicStorageDAO.findByName("zeppelin"));
        
        final Response resThree = musicStorageResourceTarget.queryParam(
            "search", "bieber"
        ).request().get();
        
        assertThat(resThree.getStatus()).isEqualTo(OK_CODE);
        assertThat(resThree.readEntity(new GenericType<List<MusicStorage>>() { }))
            .containsExactlyElementsOf(mockedMusicStorageDAO.findByName("bieber"));
    }
    
    private final void populateMockedDAOWithBasicSearches() {
        when(mockedMusicStorageDAO.findByName("haskell"))
            .thenReturn(Arrays.asList(
                new MusicStorage("Loveless",                            "", new LocalDate(1991, 11, 4), new Byte[ ] { 0 }),
                new MusicStorage("Layla and Other Assorted Love Songs", "", new LocalDate(1970, 11, 1), new Byte[ ] { 0 }),
                new MusicStorage("To Bring You My Love",                "", new LocalDate(1995, 2, 27), new Byte[ ] { 0 })
            ));
        
        when(mockedMusicStorageDAO.findByName("zeppelin"))
            .thenReturn(Arrays.asList(
                new MusicStorage("Led Zeppelin",     "", new LocalDate(1969, 1, 12),  new Byte[ ] { 0 }),
                new MusicStorage("Led Zeppelin II",  "", new LocalDate(1969, 10, 22), new Byte[ ] { 0 }),
                new MusicStorage("Led Zeppelin III", "", new LocalDate(1970, 10, 5),  new Byte[ ] { 0 }),
                new MusicStorage("Led Zeppelin IV",  "", new LocalDate(1971, 11, 8),  new Byte[ ] { 0 })
            ));
        
        when(mockedMusicStorageDAO.findByName("bieber"))
            .thenReturn(Collections.<MusicStorage>emptyList());
    }

}
