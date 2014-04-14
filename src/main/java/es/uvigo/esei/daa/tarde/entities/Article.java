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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 150, nullable = false)
    protected String name;

    @Column(nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    protected LocalDate date;

    @Lob
    @Column(length = 2000, nullable = false)
    protected String description = "";

    @Lob
    @Column(nullable = false)
    protected byte[ ] picture = new byte[ ] { 0 };

    @Column(nullable = false, name = "verified")
    protected boolean isVerified = false;

    protected Article( ) { }

    protected Article(
        final String name,
        final String description,
        final LocalDate date,
        final byte[ ] picture
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
        notNull(name, "Article's name cannot be null");
        notNull(date, "Article's date cannot be null");

        this.name = name;
        this.date = date;
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
        return this.isVerified;
    }

    public void setDescription(final String description) {
        notNull(description, "Article's description cannot be null");
        this.description = description;
    }

    public void setPicture(final byte[ ] picture) {
        notNull(picture, "Article's picture cannot be null");
        this.picture = Arrays.copyOf(picture, picture.length);
    }

    public void setVerified(final boolean isVerified) {
        this.isVerified = isVerified;
    }

    
    public final int hashCode( ) {
        final HashCodeBuilder builder = new HashCodeBuilder();

        builder.append(getName());
        builder.append(getDate());

        return builder.toHashCode();
    }

    
    public final boolean equals(final Object obj) {
        if (obj == this) return true;
        if (obj == null || !(obj instanceof Article)) return false;

        final Article that = (Article) obj;
        final EqualsBuilder builder = new EqualsBuilder();

        builder.append(that.getName(), this.getName());
        builder.append(that.getDate(), this.getDate());

        return builder.isEquals();
    }

}

