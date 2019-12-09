package io.github.portlek.versionmatched;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

final class VersionClass<T> {

    private static final char[] NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    @NotNull
    private final String rawClassName;

    @NotNull
    private final Class<? extends T> clazz;

    private VersionClass(@NotNull final String rawClassName,
                         @NotNull final Class<? extends T> clazz) {
        this.rawClassName = rawClassName;
        this.clazz = clazz;
    }

    VersionClass(@NotNull final Class<? extends T> clazz) {
        this(clazz.getSimpleName(), clazz);
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

        if (subString == -1) {
            throw new IllegalStateException("version() -> Invalid name for \"" + clazz.getSimpleName() + "\"");
        }

        return rawClassName.substring(subString);
    }

    private int versionSubString() {
        final AtomicInteger subString = new AtomicInteger();

        finalBreak:
        for (char name : rawClassName.toCharArray()) {
            for (int number : NUMBERS) {
                if (name == number) {
                    break finalBreak;
                }

            }

            subString.incrementAndGet();
        }

        return subString.get();
    }
}
