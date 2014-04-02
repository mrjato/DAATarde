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

import es.uvigo.esei.daa.tarde.daos.ComicDAO;
import es.uvigo.esei.daa.tarde.entities.Comic;

@RunWith(Parameterized.class)
public class ComicResourceTest extends ArticleResourceTest<Comic, ComicDAO> {

    @Parameters(name = "{index}: {0}")
    public static final Collection<Object[ ]> createComicData( ) {
        return Arrays.asList(new Object[ ][ ] {
            { "wolverine", new Comic[ ] {
                new Comic("Wolverine Classic Vol. 4",   new LocalDate(2006, 9, 13)),
                new Comic("Essential Wolverine Vol. 4", new LocalDate(2006, 4, 19)),
                new Comic("Wolverine  #308",            new LocalDate(2012, 1, 20))
            }},
            { "hulk", new Comic[ ] {
                new Comic("Fall of the Hulks: Red Hulk #2", new LocalDate(2010, 2, 24)),
                new Comic("Hulk: Destruction #3",           new LocalDate(2005, 9, 28)),
                new Comic("Incredible Hulk #8",             new LocalDate(2012, 5, 30)),
                new Comic("Hulk Smash Avengers #2",         new LocalDate(2013, 2, 13))
            }},
            { "daredevil", new Comic[ ] {
            }}
        });
    }

    private final String      comicListName;
    private final List<Comic> comicList;

    public ComicResourceTest(final String comicListName, final Comic [ ] comicList) {
        this.comicListName = comicListName;
        this.comicList     = Arrays.asList(comicList);
        registerResourceUnderTest(new ComicResource(mockedDAO));
    }

    @Test
    public void comic_resource_is_able_to_search_by_name( ) {
        when(mockedDAO.findByName(comicListName)).thenReturn(comicList);

        final Response response = jerseyTest.target("comics").queryParam(
            "search", comicListName
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(OK_CODE);
        assertThat(response.readEntity(
            new GenericType<List<Comic>>() { }
        )).containsExactlyElementsOf(comicList);
    }

    @Test
    public void comic_resource_returns_all_comics_when_searching_with_empty_name( ) {
        when(mockedDAO.findByName("")).thenReturn(comicList);

        final Response response = jerseyTest.target("comics").queryParam(
            "search", ""
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(OK_CODE);
        assertThat(response.readEntity(
            new GenericType<List<Comic>>() { }
        )).containsExactlyElementsOf(comicList);
    }

    @Test
    public void comic_resource_returns_a_server_error_code_when_dao_throws_exception_while_searching_by_name( ) {
        when(mockedDAO.findByName(comicListName)).thenThrow(new PersistenceException());

        final Response response = jerseyTest.target("comics").queryParam(
            "search", comicListName
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(SERVER_ERROR_CODE);
    }

    @Test
    public void comic_resource_is_able_to_insert_comics( ) {
        final Builder request = jerseyTest.target("comics").request(
            MediaType.APPLICATION_JSON
        );

        for (final Comic comic : comicList) {
            final Response response = request.post(Entity.entity(
                comic, MediaType.APPLICATION_JSON_TYPE
            ));

            assertThat(response.getStatus()).isEqualTo(OK_CODE);
            verify(mockedDAO).insert(comic);
        }
    }

    @Test
    public void comic_resource_returns_a_server_error_code_when_dao_throws_exception_while_inserting_a_comic( ) {
        final Builder request = jerseyTest.target("comics").request(
            MediaType.APPLICATION_JSON
        );

        for (final Comic comic : comicList) {
            doThrow(new PersistenceException()).when(mockedDAO).insert(comic);

            final Response response = request.post(Entity.entity(
                comic, MediaType.APPLICATION_JSON_TYPE
            ));

            assertThat(response.getStatus()).isEqualTo(SERVER_ERROR_CODE);
        }
    }

}
