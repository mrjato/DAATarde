package es.uvigo.esei.daa.tarde.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

import com.google.common.annotations.VisibleForTesting;

@Entity
@Table(name = "books")
public class Book extends Article {

	public Book( ) {
		super();
	}

	public Book(String name, String description, LocalDate date, Byte[] picture) {
		super(name, description, date, picture);
	}

	@VisibleForTesting
	public Book(String name) {
		super(name);
	}
	
	@Override
	public final boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || !(obj instanceof Book)) return false;
		
		final Book that = (Book) obj;
		final EqualsBuilder builder = new EqualsBuilder();

		builder.append(that.getName(), this.getName());
		builder.append(that.getDate(), this.getDate());

		return builder.isEquals();
	}
	
	@Override
	public final int hashCode() {
		return new HashCodeBuilder()
		    .append(getName())
		    .append(getDate())
		    .toHashCode();
	}
	
}
