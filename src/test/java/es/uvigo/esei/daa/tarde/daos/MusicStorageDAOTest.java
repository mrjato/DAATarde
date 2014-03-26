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
import es.uvigo.esei.daa.tarde.entities.MusicStorage;

@RunWith(Parameterized.class)
public class MusicStorageDAOTest extends DatabaseTest {

    @Parameters
    public static Collection<MusicStorage[ ]> createMusicStorages( ) {
        return Arrays.asList(new MusicStorage[ ][ ] {
            {
                new MusicStorage("Pulp Fiction", "", new LocalDate(), new Byte[ ] { 0 }),
                new MusicStorage("Titanic", "", new LocalDate(), new Byte[ ] { 0 }),
                new MusicStorage("Inglorious Basterds", "", new LocalDate(), new Byte[ ] { 0 })
            },
            { 
                new MusicStorage("La Princesa Mononoke", "", new LocalDate(1997, 1, 1), new Byte[ ] { 0 }),
                new MusicStorage("El viaje de Chihiro",  "", new LocalDate(2001, 1, 1), new Byte[ ] { 0 }),
                new MusicStorage("Mi vecino Totoro",     "", new LocalDate(1988, 1, 1), new Byte[ ] { 0 })
            }
        });
    }

    private MusicStorageDAO    dao;
    
    private final MusicStorage one;
    private final MusicStorage two;
    private final MusicStorage three;

    public MusicStorageDAOTest(final MusicStorage one, final MusicStorage two, final MusicStorage three) {
        this.one   = one;
        this.two   = two;
        this.three = three;
    }

    private void saveMusicStorage(final MusicStorage musicStorage) {
        if (musicStorage.getId() == null)
            entityManager.persist(musicStorage);
        else
            entityManager.merge(musicStorage);
    }

    @Before
    public void createMusicStorageDAO( ) {
        dao = new MusicStorageDAO(emFactory);
    }

    @Before
    public void insertMusicStorage( ) {
        entityManager.getTransaction().begin();
        saveMusicStorage(one);
        saveMusicStorage(two);
        saveMusicStorage(three);
        entityManager.getTransaction().commit();
    }

    @Test
    public void music_storage_dao_can_find_music_storages_by_exact_title( ) {
        final List<MusicStorage> oneFound = dao.findByName(one.getName());
        assertThat(oneFound).contains(one);

        final List<MusicStorage> twoFound = dao.findByName(two.getName());
        assertThat(twoFound).contains(two);

        final List<MusicStorage> threeFound = dao.findByName(three.getName());
        assertThat(threeFound).contains(three);
    }
    
    @Test
    public void music_storage_dao_can_find_music_storages_by_approximate_title( ) {
        final String wordOne = one.getName().split("\\s+")[0];
        final List<MusicStorage> oneFound = dao.findByName(wordOne);
        assertThat(oneFound).contains(one);
        
        final String wordTwo = two.getName().split("\\s+")[0];
        final List<MusicStorage> twoFound = dao.findByName(wordTwo);
        assertThat(twoFound).contains(two);
        
        final String wortThree = three.getName().split("\\s+")[0];
        final List<MusicStorage> threeFound = dao.findByName(wortThree);
        assertThat(threeFound).contains(three);
    }

    @Test
    public void musicStorage_dao_should_return_all_music_storages_when_searching_with_empty_title( ) {
        final List<MusicStorage> empty = dao.findByName("");
        assertThat(empty).containsOnly(one, two, three);
    }
    
}
