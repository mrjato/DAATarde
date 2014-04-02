package es.uvigo.esei.daa.tarde.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.daos.MovieDAO;
import es.uvigo.esei.daa.tarde.entities.Movie;

@RunWith(Parameterized.class)
public class MovieResourceTest extends ArticleResourceTest<Movie, MovieDAO> {

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

    private final String      movieListName;
    private final List<Movie> movieList;

    public MovieResourceTest(final String movieListName, final Movie [ ] movieList) {
        this.movieListName = movieListName;
        this.movieList     = Arrays.asList(movieList);
        registerResourceUnderTest(new MovieResource(mockedDAO));
    }

    @Test
    public void movie_resource_is_able_to_search_by_name( ) {
        when(mockedDAO.findByName(movieListName)).thenReturn(movieList);

        final Response response = jerseyTest.target("movies").queryParam(
            "search", movieListName
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(OK_CODE);
        assertThat(response.readEntity(
            new GenericType<List<Movie>>() { }
        )).containsExactlyElementsOf(movieList);
    }

    @Test
    public void movie_resource_returns_all_movies_when_searching_with_empty_name( ) {
        when(mockedDAO.findByName("")).thenReturn(movieList);

        final Response response = jerseyTest.target("movies").queryParam(
            "search", ""
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(OK_CODE);
        assertThat(response.readEntity(
            new GenericType<List<Movie>>() { }
        )).containsExactlyElementsOf(movieList);
    }

    @Test
    public void movie_resource_returns_a_server_error_code_when_dao_throws_exception_while_searching_by_name( ) {
        when(mockedDAO.findByName(movieListName)).thenThrow(new PersistenceException());

        final Response response = jerseyTest.target("movies").queryParam(
            "search", movieListName
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(SERVER_ERROR_CODE);
    }

    @Test
    public void movie_resource_is_able_to_insert_movies( ) {
        final Builder request = jerseyTest.target("movies").request(
            MediaType.APPLICATION_JSON
        );

        for (final Movie movie : movieList) {
            final Response response = request.post(Entity.entity(
                movie, MediaType.APPLICATION_JSON_TYPE
            ));

            assertThat(response.getStatus()).isEqualTo(OK_CODE);
            verify(mockedDAO).save(movie);
        }
    }

    @Test
    public void movie_resource_returns_a_server_error_code_when_dao_throws_exception_while_inserting_a_movie( ) {
        final Builder request = jerseyTest.target("movies").request(
            MediaType.APPLICATION_JSON
        );

        for (final Movie movie : movieList) {
            doThrow(new PersistenceException()).when(mockedDAO).save(movie);

            final Response response = request.post(Entity.entity(
                movie, MediaType.APPLICATION_JSON_TYPE
            ));

            assertThat(response.getStatus()).isEqualTo(SERVER_ERROR_CODE);
        }
    }

    @Test
    public void movie_resource_returns_not_verified_movies( ) {
        when(mockedDAO.findNotVerified()).thenReturn(movieList);

        final Response response = jerseyTest.target("movies").path(
            "/notVerified"
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(OK_CODE);
        assertThat(response.readEntity(
            new GenericType<List<Movie>>() { }
        )).containsExactlyElementsOf(movieList);
    }

}
