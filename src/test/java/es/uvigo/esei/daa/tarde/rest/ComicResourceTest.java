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

import es.uvigo.esei.daa.tarde.daos.ComicDAO;
import es.uvigo.esei.daa.tarde.entities.Comic;

public class ComicResourceTest extends BaseResourceTest {

    private ComicDAO  mockedComicDAO;
    private WebTarget comicResourceTarget;

    @Override
    protected JerseyTest createJerseyTest( ) {
        mockedComicDAO = mock(ComicDAO.class);

        return aJerseyTest().addResource(
            new ComicResource(mockedComicDAO)
                ).build();
    }

    @Before
    public void createWebTarget( ) {
        comicResourceTarget = jerseyTest.target("comics");
    }

    @Test
    public void comic_resource_is_able_to_search_by_title( ) {
        populateMockedDAOWithBasicSearches();

        final Response resOne = comicResourceTarget.queryParam(
            "search", "wolverine"
                ).request().get();

        assertThat(resOne.getStatus()).isEqualTo(OK_CODE);
        assertThat(resOne.readEntity(new GenericType<List<Comic>>() { }))
        .containsExactlyElementsOf(mockedComicDAO.findByName("wolverine"));

        final Response resTwo = comicResourceTarget.queryParam(
            "search", "hulk"
                ).request().get();

        assertThat(resTwo.getStatus()).isEqualTo(OK_CODE);
        assertThat(resTwo.readEntity(new GenericType<List<Comic>>() { }))
        .containsExactlyElementsOf(mockedComicDAO.findByName("hulk"));

        final Response resThree = comicResourceTarget.queryParam(
            "search", "daredevil"
                ).request().get();

        assertThat(resThree.getStatus()).isEqualTo(OK_CODE);
        assertThat(resThree.readEntity(new GenericType<List<Comic>>() { }))
        .containsExactlyElementsOf(mockedComicDAO.findByName("daredevil"));
    }

    private final void populateMockedDAOWithBasicSearches() {
        when(mockedComicDAO.findByName("wolverine"))
        .thenReturn(Arrays.asList(
            new Comic("Wolverine Classic Vol. 4",   "", new LocalDate(2006, 9, 13), new Byte[ ] { 0 }),
            new Comic("Essential Wolverine Vol. 4", "", new LocalDate(2006, 4, 19), new Byte[ ] { 0 }),
            new Comic("Wolverine  #308",            "", new LocalDate(2012, 1, 20), new Byte[ ] { 0 })
                ));

        when(mockedComicDAO.findByName("hulk"))
        .thenReturn(Arrays.asList(
            new Comic("Fall of the Hulks: Red Hulk #2", "", new LocalDate(2010, 2, 24), new Byte[ ] { 0 }),
            new Comic("Hulk: Destruction #3",           "", new LocalDate(2005, 9, 28), new Byte[ ] { 0 }),
            new Comic("Incredible Hulk #8",             "", new LocalDate(2012, 5, 30), new Byte[ ] { 0 }),
            new Comic("Hulk Smash Avengers #2",         "", new LocalDate(2013, 2, 13), new Byte[ ] { 0 })
                ));

        when(mockedComicDAO.findByName("daredevil"))
        .thenReturn(Collections.<Comic>emptyList());
    }

}
