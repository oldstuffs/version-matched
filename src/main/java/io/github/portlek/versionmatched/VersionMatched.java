package io.github.portlek.versionmatched;

import io.github.portlek.reflection.Reflection;
import org.cactoos.Scalar;
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
 * @param <T> Interface all of the classes
 */
public class VersionMatched<T> {

    /**
     * Reflection class
     *
     * @apiNote see https://github.com/portlek/reflection
     */
    private final Reflection reflection;

    /**
     * Logger class
     */
    private final Logger logger;

    /**
     * You server version (i.e. 1_14_R1, 1_8_R2)
     */
    private final String serverVersion;

    /**
     * Classes that match.
     */
    private final List<VersionClass<T>> versionClasses;

    /**
     * @param logger  Logger
     * @param classes Classes which will create objec
     *                (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    @SafeVarargs
    public VersionMatched(@NotNull Logger logger,
                          @NotNull final Class<? extends T>... classes) {
        if (classes.length == 0)
            throw new NoSuchElementException("#VersionMatched(#Logger, #Class<T>[]) -> There is not any class element!");

        this.logger = logger;
        this.reflection = new Reflection(logger);
        this.serverVersion = reflection.getCraftBukkitVersion().substring(1);
        this.versionClasses = new ListOf<>(
            new Mapped<>(
                input -> new VersionClass<>(logger, input),
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
    public T instance(Object... args) {
        final Class<? extends T> match = match();
        if (match == null)
            return null;
        else
            return reflection.newInstance(match, args);
    }

    /**
     * Matches classes
     *
     * @return class that match or throw exception
     */
    @Nullable
    private Class<? extends T> match() {
        final Scalar<VersionClass<T>> firsOf = new FirstOf<>(
            input -> input.match(serverVersion),
            versionClasses,
            () -> {
                logger.severe("VersionMatched#match() -> Couldn't find any matched class on \"" + serverVersion + "\" version!");
                return null;
            }
        );

        try {
            return firsOf.value().getVersionClass();
        } catch (Exception e) {
            return null;
        }
    }

}
