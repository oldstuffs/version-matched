package io.github.portlek.versionmatched;

import io.github.portlek.reflection.LoggerOf;
import io.github.portlek.reflection.clazz.ClassOf;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.FirstOf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

/**
 * Matches classes with your server version and choose
 * the right class for instantiating instead of you.
 *
 * @param <T> The interface of classes.
 */
public class VersionMatched<T> {

    private static final Logger LOGGER = new LoggerOf(VersionMatched.class);
    private static final String VERSION = new Version().raw();

    /**
     * Classes that match.
     */
    @NotNull
    private final List<VersionClass<T>> versionClasses;

    /**
     * @param classes Classes which will create objec
     *                (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    @SafeVarargs
    public VersionMatched(@NotNull final Class<? extends T>... classes) {
        if (classes.length == 0)
            throw new NoSuchElementException("constructor(#Logger, #Class<T>[]) -> There is not any class element!");

        this.versionClasses = new ListOf<>(
            new Mapped<>(
                VersionClass<T>::new,
                new IterableOf<>(classes)
            )
        );
    }

    /**
     * Instantiates an object which is using <T>.
     *
     * @param args Constructor arguments
     * @return the object, or throws
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public T instance(@NotNull final Object... args) {
        final Class<? extends T> match = match();

        if (match == null)
            return null;

        return (T) new ClassOf(match).getConstructor(args).create(args);
    }

    /**
     * Matches classes
     *
     * @return class that match or throw exception
     */
    @Nullable
    private Class<? extends T> match() {
        try {
            return new FirstOf<>(
                input -> input.match(VERSION),
                versionClasses,
                () -> {
                    LOGGER.severe("match() -> Couldn't find any matched class on \"" + VERSION + "\" version!");
                    return null;
                }
            ).value().getVersionClass();
        } catch (Exception e) {
            return null;
        }
    }

}
