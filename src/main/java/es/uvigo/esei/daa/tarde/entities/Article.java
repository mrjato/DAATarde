package es.uvigo.esei.daa.tarde.entities;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Arrays;

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

import com.google.common.annotations.VisibleForTesting;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "articles")
public abstract class Article {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 500, nullable = false)
    private String description;

    @Column(nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate date;

    @Lob
    @Column(nullable = false)
    private Byte[ ] picture;

    public Article( ) { }
    
    public Article(String name, String description, LocalDate date, Byte[] picture) {
    	notNull(name, "Article's name cannot be null");
    	notNull(description, "Article's description cannot be null");
    	notNull(date, "Article's date cannot be null");
    	notNull(picture, "Article's picture cannot be null");
    	
		this.name = name;
		this.description = description;
		this.date = date;
		this.picture = picture;
	}
    
    @VisibleForTesting
    public Article(final String name) {
    	this.name        = name;
    	this.description = "";
    	this.date        = new LocalDate();
    	this.picture     = new Byte[] { 0 };
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
