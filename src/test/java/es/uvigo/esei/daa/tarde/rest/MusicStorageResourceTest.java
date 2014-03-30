package es.uvigo.esei.daa.tarde.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.daos.MusicStorageDAO;
import es.uvigo.esei.daa.tarde.entities.MusicStorage;

@RunWith(Parameterized.class)
public class MusicStorageResourceTest extends BaseResourceTest {

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[ ]> createMusicData( ) {
        return Arrays.asList(new Object[ ][ ] {
            { "love", new MusicStorage[ ] {
                new MusicStorage("Loveless",                            new LocalDate(1991, 11,  4)),
                new MusicStorage("Layla and Other Assorted Love Songs", new LocalDate(1970, 11,  1)),
                new MusicStorage("To Bring You My Love",                new LocalDate(1995,  2, 27)),
                new MusicStorage("A Love Supreme",                      new LocalDate(1965,  2,  1)),
                new MusicStorage("Songs of Love and Hate",              new LocalDate(1971,  3, 17))
            }},
            { "zeppelin", new MusicStorage[ ] {
                new MusicStorage("Led Zeppelin",     new LocalDate(1969,  1, 12)),
                new MusicStorage("Led Zeppelin II",  new LocalDate(1969, 10, 22)),
                new MusicStorage("Led Zeppelin III", new LocalDate(1970, 10,  5)),
                new MusicStorage("Led Zeppelin IV",  new LocalDate(1971, 11,  8))
            }},
            { "bieber", new MusicStorage[ ] { }}
        });
    }

    private final MusicStorageDAO    mockedDAO;
    private final String             musicListName;
    private final List<MusicStorage> musicList;

    public MusicStorageResourceTest(final String musicListName, final MusicStorage [ ] musicList) {
        this.musicListName = musicListName;
        this.musicList     = Arrays.asList(musicList);

        this.mockedDAO = mock(MusicStorageDAO.class);
        registerResourceUnderTest(new MusicStorageResource(mockedDAO));
    }

    @Test
    public void music_storage_resource_is_able_to_search_by_title( ) {
        when(mockedDAO.findByName(musicListName)).thenReturn(musicList);

        final Response response = jerseyTest.target("musicstorages").queryParam(
            "search", musicListName
        ).request().get();

        assertThat(response.getStatus()).isEqualTo(OK_CODE);
        assertThat(response.readEntity(new GenericType<List<MusicStorage>>() { }))
            .containsExactlyElementsOf(musicList);
    }

}
