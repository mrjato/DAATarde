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

import es.uvigo.esei.daa.tarde.daos.MovieDAO;
import es.uvigo.esei.daa.tarde.entities.Movie;

public class MovieResourceTest extends BaseResourceTest {

    private MovieDAO  mockedMovieDAO;
    private WebTarget movieResourceTarget;

    @Override
    protected JerseyTest createJerseyTest( ) {
        mockedMovieDAO = mock(MovieDAO.class);

        return aJerseyTest().addResource(
            new MovieResource(mockedMovieDAO)
                ).build();
    }

    @Before
    public void createWebTarget( ) {
        movieResourceTarget = jerseyTest.target("movies");
    }

    @Test
    public void movie_resource_is_able_to_search_by_title( ) {
        populateMockedDAOWithBasicSearches();

        final Response resOne = movieResourceTarget.queryParam(
            "search", "matrix"
                ).request().get();

        assertThat(resOne.getStatus()).isEqualTo(OK_CODE);
        assertThat(resOne.readEntity(new GenericType<List<Movie>>() { }))
        .containsExactlyElementsOf(mockedMovieDAO.findByName("matrix"));

        final Response resTwo = movieResourceTarget.queryParam(
            "search", "future"
                ).request().get();

        assertThat(resTwo.getStatus()).isEqualTo(OK_CODE);
        assertThat(resTwo.readEntity(new GenericType<List<Movie>>() { }))
        .containsExactlyElementsOf(mockedMovieDAO.findByName("future"));

        final Response resThree = movieResourceTarget.queryParam(
            "search", "godfather"
                ).request().get();

        assertThat(resThree.getStatus()).isEqualTo(OK_CODE);
        assertThat(resThree.readEntity(new GenericType<List<Movie>>() { }))
        .containsExactlyElementsOf(mockedMovieDAO.findByName("godfather"));
    }

    private final void populateMockedDAOWithBasicSearches() {
        when(mockedMovieDAO.findByName("matrix"))
        .thenReturn(Arrays.asList(
            new Movie("The Matrix",             "", new LocalDate(1999, 1, 1), new Byte[ ] { 0 }),
            new Movie("The Matrix Reloaded",    "", new LocalDate(2003, 1, 1), new Byte[ ] { 0 }),
            new Movie("The Matrix Revolutions", "", new LocalDate(2003, 1, 1), new Byte[ ] { 0 })
                ));

        when(mockedMovieDAO.findByName("future"))
        .thenReturn(Arrays.asList(
            new Movie("Back to the Future",          "", new LocalDate(1985, 1, 1), new Byte[ ] { 0 }),
            new Movie("Back to the Future Part II",  "", new LocalDate(1989, 1, 1), new Byte[ ] { 0 }),
            new Movie("Back to the Future Part III", "", new LocalDate(1990, 1, 1), new Byte[ ] { 0 }),
            new Movie("X-Men: Days of Future Past",  "", new LocalDate(2014, 1, 1), new Byte[ ] { 0 })
                ));

        when(mockedMovieDAO.findByName("godfather"))
        .thenReturn(Collections.<Movie>emptyList());
    }

}
