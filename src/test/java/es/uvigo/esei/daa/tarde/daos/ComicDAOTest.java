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

import es.uvigo.esei.daa.tarde.DatabaseTest;
import es.uvigo.esei.daa.tarde.entities.Comic;

@RunWith(Parameterized.class)
public class ComicDAOTest extends DatabaseTest {

    @Parameters
    public static Collection<Comic[ ]> createComics( ) {
        return Arrays.asList(new Comic[ ][ ] {
            {
                new Comic("X-Men",       "", new LocalDate(), new Byte[ ] { 0 }),
                new Comic("Spiderman",   "", new LocalDate(), new Byte[ ] { 0 }),
                new Comic("Zipi y Zape", "", new LocalDate(), new Byte[ ] { 0 })
            },
            { 
                new Comic("Iron Patriot", "", new LocalDate(2014, 3, 26), new Byte[ ] { 0 }),
                new Comic("Captain America: Homecoming", "", new LocalDate(2014, 3, 14), new Byte[ ] { 0 }),
                new Comic("Miracle Man", "", new LocalDate(2014, 3, 26), new Byte[ ] { 0 })
            }
        });
    }

    private ComicDAO    dao;
    
    private final Comic one;
    private final Comic two;
    private final Comic three;

    public ComicDAOTest(final Comic one, final Comic two, final Comic three) {
        this.one   = one;
        this.two   = two;
        this.three = three;
    }

    private void saveComic(final Comic comic) {
        if (comic.getId() == null)
            entityManager.persist(comic);
        else
            entityManager.merge(comic);
    }

    @Before
    public void createComicDAO( ) {
        dao = new ComicDAO(emFactory);
    }

    @Before
    public void insertComic( ) {
        entityManager.getTransaction().begin();
        saveComic(one);
        saveComic(two);
        saveComic(three);
        entityManager.getTransaction().commit();
    }

    @Test
    public void comic_dao_can_find_comics_by_exact_title( ) {
        final List<Comic> oneFound = dao.findByName(one.getName());
        assertThat(oneFound).contains(one);

        final List<Comic> twoFound = dao.findByName(two.getName());
        assertThat(twoFound).contains(two);

        final List<Comic> threeFound = dao.findByName(three.getName());
        assertThat(threeFound).contains(three);
    }
    
    @Test
    public void comic_dao_can_find_comics_by_approximate_title( ) {
        final String wordOne = one.getName().split("\\s+")[0];
        final List<Comic> oneFound = dao.findByName(wordOne);
        assertThat(oneFound).contains(one);
        
        final String wordTwo = two.getName().split("\\s+")[0];
        final List<Comic> twoFound = dao.findByName(wordTwo);
        assertThat(twoFound).contains(two);
        
        final String wortThree = three.getName().split("\\s+")[0];
        final List<Comic> threeFound = dao.findByName(wortThree);
        assertThat(threeFound).contains(three);
    }

    @Test
    public void comic_dao_should_return_all_comic_when_searching_with_empty_title( ) {
        final List<Comic> empty = dao.findByName("");
        assertThat(empty).containsOnly(one, two, three);
    }
    
}
