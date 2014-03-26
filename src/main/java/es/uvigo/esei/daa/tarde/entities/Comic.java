package es.uvigo.esei.daa.tarde.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

@Entity
@Table(name = "comics")
public class Comic extends Article {

    protected Comic( ) {
        super();
    }

    public Comic(
        final String    name,
        final String    description,
        final LocalDate date,
        final Byte[ ]   picture) {
        super(name, description, date, picture);
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
