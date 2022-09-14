package data.models;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * From https://en.wikipedia.org/w/index.php?title=Unordered_pair&oldid=1018652333:
 * "In mathematics, an unordered pair or pair set is a set of the form {a, b}, i.e. a set having two elements a and b with no particular relation between them, where {a, b} = {b, a}.
 *  In contrast, an ordered pair (a, b) has a as its first element and b as its second element, which means (a, b) ≠ (b, a).
 *
 * While the two elements of an ordered pair (a, b) need not be distinct, modern authors only call {a, b} an unordered pair if a ≠ b."
 */

public class UnorderedPair<T> {
    private final T value1;
    private final T value2;

    public UnorderedPair(@NotNull final T value1, @NotNull final T value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T getValue1() {
        return value1;
    }

    public T getValue2() {
        return value2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnorderedPair<?> that = (UnorderedPair<?>) o;
        return (Objects.equals(value1, that.value1) && Objects.equals(value2, that.value2)) ||
                (Objects.equals(value1, that.value2) && Objects.equals(value2, that.value1));
    }

    @Override
    public int hashCode() {
        final int firstValueHash = value1.hashCode();
        final int secondValueHash = value2.hashCode();
        return Math.min(firstValueHash, secondValueHash) * 31 + Math.max(firstValueHash, secondValueHash);
    }
}
