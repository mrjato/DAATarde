package es.uvigo.esei.daa.tarde.daos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import es.uvigo.esei.daa.tarde.DatabaseTest;
import es.uvigo.esei.daa.tarde.entities.Movie;

@RunWith(Parameterized.class)
public class MovieDAOTest extends DatabaseTest {

    @Parameters
    public static Collection<Movie[ ]> createMovies( ) {
        return Arrays.asList(new Movie[ ][ ] {
            {
                new Movie("Pulp Fiction", "", new LocalDate(), new Byte[ ] { 0 }),
                new Movie("Titanic", "", new LocalDate(), new Byte[ ] { 0 }),
                new Movie("Inglorious Basterds", "", new LocalDate(), new Byte[ ] { 0 })
            },
            { 
                new Movie("La Princesa Mononoke", "", new LocalDate(1997, 1, 1), new Byte[ ] { 0 }),
                new Movie("El viaje de Chihiro",  "", new LocalDate(2001, 1, 1), new Byte[ ] { 0 }),
                new Movie("Mi vecino Totoro",     "", new LocalDate(1988, 1, 1), new Byte[ ] { 0 })
            }
        });
    }

    private MovieDAO    dao;
    
    private final Movie one;
    private final Movie two;
    private final Movie three;

    public MovieDAOTest(final Movie one, final Movie two, final Movie three) {
        this.one   = one;
        this.two   = two;
        this.three = three;
    }

    private void saveMovie(final Movie movie) {
        if (movie.getId() == null)
            entityManager.persist(movie);
        else
            entityManager.merge(movie);
    }

    @Before
    public void createMovieDAO( ) {
        dao = new MovieDAO();
    }

    @Before
    public void insertMovie( ) {
        entityManager.getTransaction().begin();
        saveMovie(one);
        saveMovie(two);
        saveMovie(three);
        entityManager.getTransaction().commit();
    }

    @Test
    public void movie_dao_can_find_movies_by_exact_title( ) {
        final List<Movie> oneFound = dao.findByName(one.getName());
        assertThat(oneFound).contains(one);

        final List<Movie> twoFound = dao.findByName(two.getName());
        assertThat(twoFound).contains(two);

        final List<Movie> threeFound = dao.findByName(three.getName());
        assertThat(threeFound).contains(three);
    }
    
    @Test
    public void movie_dao_can_find_movies_by_approximate_title( ) {
        final String wordOne = one.getName().split("\\s+")[0];
        final List<Movie> oneFound = dao.findByName(wordOne);
        assertThat(oneFound).contains(one);
        
        final String wordTwo = two.getName().split("\\s+")[0];
        final List<Movie> twoFound = dao.findByName(wordTwo);
        assertThat(twoFound).contains(two);
        
        final String wortThree = three.getName().split("\\s+")[0];
        final List<Movie> threeFound = dao.findByName(wortThree);
        assertThat(threeFound).contains(three);
    }
    
    @Test
    public void movie_dao_finds_movies_ignoring_case( ) {
        final String word = one.getName().split("\\s+")[0];
        
        final List<Movie> upperCasedWord = dao.findByName(word.toUpperCase());
        assertThat(upperCasedWord).contains(one);
        
        final List<Movie> lowerCasedWord = dao.findByName(word.toLowerCase());
        assertThat(lowerCasedWord).contains(one);
        
        final Random random = new Random(); 
        final StringBuilder builder = new StringBuilder();
        for (char c : word.toCharArray()) {
             if (random.nextBoolean())
                 builder.append(Character.toUpperCase(c));
             else
                 builder.append(Character.toLowerCase(c));
        }
        
        final List<Movie> randomizedCaseWord = dao.findByName(builder.toString());
        assertThat(randomizedCaseWord).contains(one);
    }

    @Test
    public void movie_dao_should_return_all_movies_when_searching_with_empty_title( ) {
        final List<Movie> empty = dao.findByName("");
        assertThat(empty).containsOnly(one, two, three);
    }
    
}
