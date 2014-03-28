package es.uvigo.esei.daa.tarde.rest;

import static es.uvigo.esei.daa.tarde.rest.JerseyTestBuilder.aJerseyTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.test.JerseyTest;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.daos.MovieDAO;
import es.uvigo.esei.daa.tarde.entities.Movie;

@RunWith(Parameterized.class)
public class MovieResourceTest extends BaseResourceTest {

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[ ]> createMovieData( ) {
        return Arrays.asList(new Object[ ][ ] {
            { "matrix", new Movie[ ] {
                new Movie("The Matrix",             new LocalDate(1999, 1, 1)),
                new Movie("The Matrix Reloaded",    new LocalDate(2003, 1, 1)),
                new Movie("The Matrix Revolutions", new LocalDate(2003, 1, 1))
            }},
            { "future", new Movie[ ] {
                new Movie("Back to the Future",          new LocalDate(1985, 1, 1)),
                new Movie("Back to the Future Part II",  new LocalDate(1989, 1, 1)),
                new Movie("Back to the Future Part III", new LocalDate(1990, 1, 1)),
                new Movie("X-Men: Days of Future Past",  new LocalDate(2014, 1, 1))
            }},
            { "godfather", new Movie[ ] { }}
        });
    }

    private final MovieDAO    mockedDAO;
    private final String      movieListName;
    private final List<Movie> movieList;

    public MovieResourceTest(final String movieListName, final Movie [ ] movieList) {
        this.mockedDAO     = mock(MovieDAO.class);
        this.movieListName = movieListName;
        this.movieList     = Arrays.asList(movieList);
    }

    @Override
    protected JerseyTest createJerseyTest( ) {
        return aJerseyTest().addResource(
            new MovieResource(mockedDAO)
        ).build();
    }

    @Test
    public void movie_resource_is_able_to_search_by_title( ) {
        when(mockedDAO.findByName(movieListName)).thenReturn(movieList);

        final Response response = jerseyTest.target("movies").queryParam(
            "search", movieListName
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(OK_CODE);
        assertThat(response.readEntity(new GenericType<List<Movie>>() { }))
            .containsExactlyElementsOf(movieList);
    }

}
