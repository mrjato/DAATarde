package es.uvigo.esei.daa.tarde.entities.articles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

@Entity
@Table(name = "comics")
public class Comic extends Article {
	@Column(length = 150, nullable = true)
	protected String serie;
	
	@Column(length = 150, nullable = true)
	protected String editorial;
	
	@Column(length = 150, nullable = true)
	protected String collection;
	
    protected Comic( ) {
        super();
    }

    public Comic(
        final String    name,
        final String    description,
        final LocalDate date,
        final String    serie,
        final String    collection,
        final String    editorial,
        final byte[ ]   picture
    ) {
        super(name, description, date, picture);
    }

    public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public Comic(final String name, final LocalDate date) {
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
        if (obj == null || !(obj instanceof Comic)) return false;

        final Comic that = (Comic) obj;
        final EqualsBuilder builder = new EqualsBuilder();

        builder.append(that.getName(), this.getName());
        builder.append(that.getDate(), this.getDate());

        return builder.isEquals();
    }

}
