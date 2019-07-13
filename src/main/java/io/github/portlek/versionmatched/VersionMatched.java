package io.github.portlek.versionmatched;

import io.github.portlek.reflection.Reflection;
import org.cactoos.Scalar;
import org.cactoos.iterable.IterableOf;
import org.cactoos.list.ListOf;
import org.cactoos.list.Mapped;
import org.cactoos.scalar.FirstOf;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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
     * Classes that match.
     */
    private final List<VersionClass<T>> versionClasses;

    /**
     * You server version (i.e. 1_14_R1)
     */
    private final String serverVersion;

    /**
     * @param reflection Reflection class
     * @param classes    Classes which will create object
     *                   (i.e. "Cmd1_14_R1.class")
     *
     * usage new VersionMatched(
     *          new Reflection(getLogger()),
     *          CmdRegistry1_14_R1.class,
     *          CommandRegistry1_13_R2.class,
     *          andSooooOnnnnnn1_13_R1.class
     *       );
     */
    @SafeVarargs
    public VersionMatched(@NotNull Reflection reflection,
                          @NotNull final Class<? extends T>... classes) {
        this.reflection = reflection;
        this.versionClasses = new ListOf<>(
            new Mapped<>(
                VersionClass<T>::new,
                new IterableOf<>(classes)
            )
        );
        this.serverVersion = reflection.getCraftBukkitVersion().substring(1);
    }

    /**
     * Instantiates an object which is using <T>.
     *
     * @param args Constructor arguments
     * @return the object, or throws
     */
    public T instance(Object... args) {
        return reflection.newInstance(match(), args);
    }

    /**
     * Matches classes
     *
     * @return class that match or throw exception
     */
    private Class<? extends T> match() {
        final Scalar<VersionClass<T>> firsOf = new FirstOf<>(
            input -> input.match(serverVersion),
            versionClasses,
            () -> {
                throw new RuntimeException(
                    "Unsupported version \"" + serverVersion + "\", report this to developers of core"
                );
            }
        );

        try {
            return firsOf.value().getVersionClass();
        } catch (Exception e) {
            throw new RuntimeException(
                "#match() couldn't find any matched class!"
            );
        }
    }

}
