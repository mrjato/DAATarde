package es.uvigo.esei.daa.tarde.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long      id;

    @Column(length = 50)
    private String    name;

    @Column(length = 500)
    private String    description;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate date;

    @Lob
    private Byte[ ]   picture;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setPicture(Byte[ ] picture) {
        this.picture = picture;
    }
}
