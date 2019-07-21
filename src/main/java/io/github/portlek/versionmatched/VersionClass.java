package io.github.portlek.versionmatched;

import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOfChars;
import org.cactoos.scalar.FirstOf;
import org.cactoos.scalar.Or;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

class VersionClass<T> {

    private final static char[] NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private final Logger logger;
    private final String rawClassName;
    private final Class<? extends T> clazz;

    private VersionClass(@NotNull final Logger logger,
                         @NotNull final String rawClassName,
                         @NotNull final Class<? extends T> clazz) {
        this.logger = logger;
        this.rawClassName = rawClassName;
        this.clazz = clazz;
    }

    VersionClass(@NotNull final Logger logger,
                 @NotNull final Class<? extends T> clazz) {
        this(logger, clazz.getSimpleName(), clazz);
    }

    @NotNull
    Class<? extends T> getVersionClass() {
        return clazz;
    }

    boolean match(@NotNull final String version) {
        return version().equals(version);
    }

    @NotNull
    private String version() {
        final int subString = versionSubString();

        if (subString == -1)
            logger.severe("VersionClass#version() -> Invalid name for \"" + clazz.getSimpleName() + "\"");

        return rawClassName.substring(subString);
    }

    private int versionSubString() {
        final AtomicInteger subString = new AtomicInteger();
        final Scalar<Character> firstOf = new FirstOf<>(
            input -> {
                final boolean or = new Or(
                    number -> input.charValue() == number,
                    new IterableOfChars(NUMBERS)
                ).value();

                if (or)
                    return true;

                subString.incrementAndGet();
                return false;
            },
            new IterableOfChars(rawClassName.toCharArray()),
            () -> 'e'
        );

        try {
            firstOf.value();
        } catch (Exception e) {
            return -1;
        }

        return subString.get();
    }
}
