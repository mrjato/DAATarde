package es.uvigo.esei.daa.tarde.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name = "articles")
public class Article implements Serializable
{

    public static enum Category
    {
        BOOK, COMIC, MOVIE, ALBUM
    }

    private static final long serialVersionUID = -8281385147256784439L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 139, nullable = false)
    private String title;
    
    @Column(length = 139, nullable = false)
    private String author;

    @Column(length = 5000)
    private String description;

    @Lob
    private Byte[ ] image;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Category category;

    @Column(name = "release_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate releaseDate;

    public final Long getId( )
    {
        return id;
    }

    public final String getTitle( )
    {
        return title;
    }

    public final void setTitle(final String title)
    {
        this.title = title;
    }

    public final String getAuthor( )
    {
        return author;
    }

    public final void setAuthor(final String author)
    {
        this.author = author;
    }

    public final String getDescription( )
    {
        return description;
    }

    public final void setDescription(final String description)
    {
        this.description = description;
    }

    public final Byte[ ] getImage( )
    {
        return image;
    }

    public final void setImage(final Byte[ ] image)
    {
        this.image = image;
    }

    public final Category getCategory( )
    {
        return category;
    }

    public final void setCategory(final Category category)
    {
        this.category = category;
    }

    public final LocalDate getReleaseDate( )
    {
        return releaseDate;
    }

    public final void setReleaseDate(final LocalDate releaseDate)
    {
        this.releaseDate = releaseDate;
    }

}
