package es.uvigo.esei.daa.tarde.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

@Entity
@Table(name = "movies")
public class Movie extends Article {

    protected Movie( ) {
        super();
    }

    public Movie(
        final String    name,
        final String    description,
        final LocalDate date,
        final Byte[ ]   picture
    ) {
        super(name, description, date, picture);
    }

    public Movie(final String name, final LocalDate date) {
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
        if (obj == null || !(obj instanceof Movie)) return false;

        final Movie that = (Movie) obj;
        final EqualsBuilder builder = new EqualsBuilder();

        builder.append(that.getName(), this.getName());
        builder.append(that.getDate(), this.getDate());

        return builder.isEquals();
    }

}
