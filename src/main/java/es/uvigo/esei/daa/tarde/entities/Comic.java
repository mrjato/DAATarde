package es.uvigo.esei.daa.tarde.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name = "comics")
public class Comic extends Article {

    protected Comic( ) {
        super();
    }

    public Comic(
        final String    name,
        final String    description,
        final LocalDate date,
        final byte[ ]   picture
    ) {
        super(name, description, date, picture);
    }

    public Comic(final String name, final LocalDate date) {
        super(name, date);
    }

    

}
