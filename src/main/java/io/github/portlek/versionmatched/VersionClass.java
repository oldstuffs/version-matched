package io.github.portlek.versionmatched;

import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.annotations.NotNull;

final class VersionClass<T> {

    private static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

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
        return this.clazz;
    }

    boolean match(@NotNull final String version) {
        return this.version().equals(version);
    }

    @NotNull
    private String version() {
        final int sub = this.versionSubString();
        if (sub == -1) {
            throw new IllegalStateException("version() -> Invalid name for " + '"' + this.clazz.getSimpleName() + '"');
        }
        return this.rawClassName.substring(sub);
    }

    private int versionSubString() {
        final AtomicInteger subString = new AtomicInteger();
        finalBreak:
        for (final char name : this.rawClassName.toCharArray()) {
            for (final int number : VersionClass.NUMBERS) {
                if (name == number) {
                    break finalBreak;
                }
            }
            subString.incrementAndGet();
        }
        return subString.get();
    }

}
