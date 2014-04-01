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
    private Long        id;

    @Column(length = 150, nullable = false)
    protected String    name;

    @Lob
    @Column(length = 2000, nullable = false)
    protected String    description;

    @Column(nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    protected LocalDate date;

    @Lob
    @Column(nullable = false)
    protected byte[ ]   picture;


    @Column(nullable = false)
    protected boolean   isVerified;

    protected Article( ) {
    }

    protected Article(
        final String name,
        final String description,
        final LocalDate date,
        final byte[ ] picture) {
        notNull(name, "Article's name cannot be null");
        notNull(description, "Article's description cannot be null");
        notNull(date, "Article's date cannot be null");
        notNull(picture, "Article's picture cannot be null");

        this.name = name;
        this.description = description;
        this.date = date;
        this.picture = picture;
        this.isVerified = false;
    }

    protected Article(final String name, final LocalDate date) {
        this(name, "", date, new byte[ ] { 0 });
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

    public byte[ ] getPicture( ) {
        return picture;
    }

    public boolean isVerified( ) {
        return isVerified;
    }

    public void setDescription(final String description) {
        notNull(description, "Article's description cannot be null");
        this.description = description;
    }

    public void setPicture(final byte[ ] picture) {
        notNull(picture, "Article's picture cannot be null");
        this.picture = Arrays.copyOf(picture, picture.length);
    }

    public void setVerified(boolean verified) {
        this.isVerified = verified;
    }

    @Override
    public abstract int hashCode( );

    @Override
    public abstract boolean equals(final Object obj);

}
