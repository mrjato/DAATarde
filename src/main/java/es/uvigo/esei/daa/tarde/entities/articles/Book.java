package es.uvigo.esei.daa.tarde.entities.articles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

@Entity
@Table(name = "books")

public class Book extends Article {
	@Column(length = 150, nullable = true)
	protected String author;
	
	@Column(length = 150, nullable = true)
	protected String editorial;
	
    protected Book( ) {
        super();
    }

    public Book(
        final String    name,
        final String    description,
        final LocalDate date,
        final String    author,
        final String    editorial,
        final byte[ ]   picture
    ) {
        super(name, description, date, picture);
    }

    public Book(final String name, final LocalDate date) {
        super(name, date);
    }

    @Override
    public final int hashCode( ) {
        final HashCodeBuilder builder = new HashCodeBuilder();

        builder.append(getName());
        builder.append(getDate());

        return builder.toHashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == this) return true;
        if (obj == null || !(obj instanceof Book)) return false;

        final Book that = (Book) obj;
        final EqualsBuilder builder = new EqualsBuilder();

        builder.append(that.getName(), this.getName());
        builder.append(that.getDate(), this.getDate());

        return builder.isEquals();
    }

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
    
}
