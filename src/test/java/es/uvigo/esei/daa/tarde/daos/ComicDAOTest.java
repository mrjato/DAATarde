package es.uvigo.esei.daa.tarde.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.TestUtils;
import es.uvigo.esei.daa.tarde.entities.Book;
import es.uvigo.esei.daa.tarde.entities.Comic;

@RunWith(Parameterized.class)
public class ComicDAOTest extends BaseDAOTest {

    @Parameters(name = "{index}: {0}")
    public static Collection<Object[ ]> createComicData( ) {
        return Arrays.asList(new Object[ ][ ] {
            { "assorted", new Comic[ ] {
                new Comic("X-Men",       new LocalDate()),
                new Comic("Spiderman",   new LocalDate()),
                new Comic("Zipi y Zape", new LocalDate())
            }},
            { "marvel", new Comic[ ] {
                new Comic("Iron Patriot",                new LocalDate(2014, 3, 26)),
                new Comic("Captain America: Homecoming", new LocalDate(2014, 3, 14)),
                new Comic("Miracle Man",                 new LocalDate(2014, 3, 26))
            }}
        });
    }

    private ComicDAO dao;
    private final List<Comic> comicList;

    public ComicDAOTest(final String _, final Comic[ ] comicList) {
        this.comicList = Arrays.asList(comicList);
    }

    @Before
    public void createComicDAO( ) {
        dao = new ComicDAO();
    }

    @Before
    public void insertComics( ) {
        entityManager.getTransaction().begin();
        for (final Comic c : comicList) {
            if (c.getId() == null) entityManager.persist(c);
            else                   entityManager.merge(c);
        }
        entityManager.getTransaction().commit();
    }

    @Test
    public void comic_dao_can_find_comics_by_exact_title( ) {
        for (final Comic comic : comicList) {
            final List<Comic> found = dao.findByName(comic.getName());
            assertThat(found).contains(comic);
        }
    }

    @Test
    public void comic_dao_can_find_comics_by_approximate_title( ) {
        for (final Comic comic : comicList) {
            final String word = comic.getName().split("\\s+")[0];
            final List<Comic> found = dao.findByName(word);
            assertThat(found).contains(comic);
        }
    }

    @Test
    public void comic_dao_finds_comics_ignoring_case( ) {
        for (final Comic comic : comicList) {
            final String word = comic.getName().split("\\s+")[0];

            final List<Comic> upper = dao.findByName(word.toUpperCase());
            assertThat(upper).contains(comic);

            final List<Comic> lower = dao.findByName(word.toLowerCase());
            assertThat(lower).contains(comic);

            final List<Comic> rand  = dao.findByName(TestUtils.randomizeCase(word));
            assertThat(rand).contains(comic);
        }
    }

    @Test
    public void comic_dao_should_return_all_comic_when_searching_with_empty_title( ) {
        final List<Comic> empty = dao.findByName("");
        assertThat(empty).isEqualTo(comicList);
    }
    
    @Test
    public void comic_dao_can_insert_comics( ) {
            Comic comic = new Comic("Miracle Man",   new LocalDate(2014, 3, 26));
            dao.insert(comic);         
            assertThat(entityManager.find(Comic.class, comic.getId())).isEqualTo(comic);
    }

}
