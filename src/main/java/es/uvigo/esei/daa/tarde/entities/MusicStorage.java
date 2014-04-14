package es.uvigo.esei.daa.tarde.entities;

import javax.persistence.Entity;
import javax.persistence.Table;


import org.joda.time.LocalDate;

@Entity
@Table(name = "music_storages")
public class MusicStorage extends Article {

    protected MusicStorage( ) {
        super();
    }

    public MusicStorage(
        final String    name,
        final String    description,
        final LocalDate date,
        final byte[ ]   picture
    ) {
        super(name, description, date, picture);
    }

    public MusicStorage(final String name, final LocalDate date) {
        super(name, date);
    }

    
}
