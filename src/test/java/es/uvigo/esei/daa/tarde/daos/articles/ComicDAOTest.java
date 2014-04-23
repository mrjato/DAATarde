package es.uvigo.esei.daa.tarde.daos.articles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.PersistenceException;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.TestUtils;
import es.uvigo.esei.daa.tarde.daos.BaseDAOTest;
import es.uvigo.esei.daa.tarde.entities.articles.Comic;

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

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private ComicDAO dao;
    private final List<Comic> comicList;

    public ComicDAOTest(final String _, final Comic[ ] comicList) {
        this.comicList = Arrays.asList(comicList);
    }

    private void unverifyComic(final Comic c) {
        entityManager.getTransaction().begin();

        final Comic comic = entityManager.find(Comic.class, c.getId());
        comic.setVerified(false);
        entityManager.merge(comic);

        entityManager.getTransaction().commit();
    }

    @Before
    public void createComicDAO( ) {
        dao = new ComicDAO();
    }

    @Before
    public void insertComics( ) {
        entityManager.getTransaction().begin();
        for (final Comic c : comicList) {
            c.setVerified(true);
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
        assertThat(empty).containsAll(comicList);
    }

    @Test
    public void comic_dao_should_ignore_non_verified_comics_when_searching_by_name( ) {
        for (final Comic comic : comicList) {
            unverifyComic(comic);

            final List<Comic> found = dao.findByName(comic.getName());
            assertThat(found).doesNotContain(comic);
        }
    }

    @Test
    public void comic_dao_can_insert_comics( ) {
        for (final Comic comic : comicList) {
            final Comic inserted = new Comic(comic.getName(), comic.getDate());
            dao.save(inserted);

            final Long id = inserted.getId();
            assertThat(id).isNotNull();

            final Comic found = entityManager.find(Comic.class, id);
            assertThat(found).isEqualTo(inserted);
        }
    }

    @Test
    public void comic_dao_can_update_comics( ) {
        for (final Comic c : comicList) {
            final Comic comic = entityManager.find(Comic.class, c.getId());
            comic.setVerified(!comic.isVerified());

            dao.save(comic);

            final Comic found = entityManager.find(Comic.class, comic.getId());
            assertThat(found.isVerified()).isEqualTo(comic.isVerified());
        }
    }

    @Test
    public void comic_dao_should_throw_an_exception_when_inserting_an_already_inserted_comic( ) {
        thrown.expect(PersistenceException.class);
        dao.insert(comicList.get(0));
    }
}
