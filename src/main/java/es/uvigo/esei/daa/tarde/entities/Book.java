package es.uvigo.esei.daa.tarde.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.joda.time.LocalDate;

@Entity
@Table(name = "books")
public class Book extends Article {

    protected Book( ) {
        super();
    }

    public Book(
        final String    name,
        final String    description,
        final LocalDate date,
        final byte[ ]   picture
    ) {
        super(name, description, date, picture);
    }

    public Book(final String name, final LocalDate date) {
        super(name, date);
    }

   

}
