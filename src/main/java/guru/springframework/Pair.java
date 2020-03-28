package guru.springframework;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Abhijit.Ghonge
 */
public class Pair {

    private final String from;
    private final String to;

    public static Pair of(String from, String to){
        return new Pair(from, to);
    }

    public static Pair identity(String currency){
        return new Pair(currency, currency);
    }

    private Pair(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        return new EqualsBuilder()
                .append(from, pair.from)
                .append(to, pair.to)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(from)
                .append(to)
                .toHashCode();
    }
}
