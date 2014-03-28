package es.uvigo.esei.daa.tarde.entities;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "articles")
public abstract class Article {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 50, nullable = false)
    protected String name;

    @Column(length = 500, nullable = false)
    protected String description;

    @Column(nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    protected LocalDate date;

    @Lob
    @Column(nullable = false)
    protected Byte[ ] picture;

    protected Article( ) { }

    protected Article(
        final String    name,
        final String    description,
        final LocalDate date,
        final Byte[ ]   picture
            ) {
        notNull(name, "Article's name cannot be null");
        notNull(description, "Article's description cannot be null");
        notNull(date, "Article's date cannot be null");
        notNull(picture, "Article's picture cannot be null");

        this.name        = name;
        this.description = description;
        this.date        = date;
        this.picture     = picture;
    }

    protected Article(final String name, final LocalDate date) {
        this(name, "", date, new Byte[ ] { 0 });
    }

    public Long getId( ) {
        return id;
    }

    public String getName( ) {
        return name;
    }

    public String getDescription( ) {
        return description;
    }

    public LocalDate getDate( ) {
        return date;
    }

    public Byte[ ] getPicture( ) {
        return picture;
    }

    public void setDescription(final String description) {
        notNull(description, "Article's description cannot be null");
        this.description = description;
    }

    public void setPicture(final Byte[ ] picture) {
        notNull(picture, "Article's picture cannot be null");
        this.picture = Arrays.copyOf(picture, picture.length);
    }

    @Override
    public abstract int hashCode( );

    @Override
    public abstract boolean equals(final Object obj);

}
