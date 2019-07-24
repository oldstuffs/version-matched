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
import java.util.logging.Logger;

/**
 * Matches classes with your server version and choose
 * the right class for instantiating instead of you.
 *
 * @param <T> The interface of classes.
 */
public class VersionMatched<T> {

    private static final Logger LOGGER = new LoggerOf(VersionMatched.class);

    /**
     * Version of the server, pattern must be like that;
     * 1_14_R1
     * 1_13_R2
     */
    private final String rawVersion;

    /**
     * Classes that match.
     */
    @NotNull
    private final List<VersionClass<T>> versionClasses;

    /**
     * @param rawVersion     Raw server version text
     *                       (i.e 1_14_R1, 1_13_R1)
     * @param versionClasses Classes which will create object
     *                       (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    public VersionMatched(@NotNull final String rawVersion, @NotNull final List<VersionClass<T>> versionClasses) {
        this.rawVersion = rawVersion;
        this.versionClasses = versionClasses;
    }

    /**
     * @param version        Server version
     * @param versionClasses Classes which will create object
     *                       (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    public VersionMatched(@NotNull final Version version, @NotNull final List<VersionClass<T>> versionClasses) {
        this(version.raw(), versionClasses);
    }

    /**
     * @param rawVersion     Raw server version text
     *                       (i.e 1_14_R1, 1_13_R1)
     * @param versionClasses Classes which will create object
     *                       (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    @SafeVarargs
    public VersionMatched(@NotNull final String rawVersion, @NotNull final Class<? extends T>... versionClasses) {
        this(
            rawVersion,
            new ListOf<>(
                new Mapped<>(
                    VersionClass<T>::new,
                    new IterableOf<>(versionClasses)
                )
            )
        );
    }

    /**
     * @param versionClasses Classes which will create object
     *                       (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    @SafeVarargs
    public VersionMatched(@NotNull final Class<? extends T>... versionClasses) {
        this(
            new Version(),
            new ListOf<>(
                new Mapped<>(
                    VersionClass<T>::new,
                    new IterableOf<>(versionClasses)
                )
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
     * Instantiates an object which is using <T>.
     *
     * @param args Constructor arguments
     * @return the object, or throws
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public T instancePrimitive(@NotNull final Object... args) {
        final Class<? extends T> match = match();

        if (match == null)
            return null;

        return (T) new ClassOf(match).getPrimitiveConstructor(args).create(args);
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
                input -> input.match(rawVersion),
                versionClasses,
                () -> {
                    LOGGER.severe("match() -> Couldn't find any matched class on \"" + rawVersion + "\" version!");
                    return null;
                }
            ).value().getVersionClass();
        } catch (Exception e) {
            return null;
        }
    }

}
