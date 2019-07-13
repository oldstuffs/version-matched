package io.github.portlek.versionmatched;

import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOfChars;
import org.cactoos.scalar.And;
import org.cactoos.scalar.FirstOf;
import org.jetbrains.annotations.NotNull;

class VersionClass<T> {

    private final static char[] NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private final String rawClassName;
    private final Class<? extends T> clazz;

    private VersionClass(@NotNull final String rawClassName,
                         @NotNull final Class<? extends T> clazz) {
        this.rawClassName = rawClassName;
        this.clazz = clazz;
    }

    VersionClass(@NotNull final Class<? extends T> clazz) {
        this(clazz.getSimpleName(), clazz);
    }

    Class<? extends T> getVersionClass() {
        return clazz;
    }

    boolean match(@NotNull final String version) {
        return version().equals(version);
    }

    @NotNull
    private String version() {
        return versionSubString() == -1
            ? ""
            : rawClassName.substring(versionSubString());
    }

    private int versionSubString() {
        final Scalar<Character> scalar = new FirstOf<>(
            input -> new And(
                number -> input.charValue() == number,
                new IterableOfChars(NUMBERS)
            ).value(),
            new IterableOfChars(rawClassName.toCharArray()),
            () -> 'e'
        );

        try {
            return scalar.value() == 'e'
                ? -1
                : scalar.value();
        } catch (Exception e) {
            return -1;
        }
    }
}
