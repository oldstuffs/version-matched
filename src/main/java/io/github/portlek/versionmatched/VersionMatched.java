package io.github.portlek.versionmatched;

import io.github.portlek.reflection.RefConstructed;
import io.github.portlek.reflection.clazz.ClassOf;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Matches classes with your server version and choose
 * the right class for instantiating instead of you.
 *
 * @param <T> The interface of classes.
 */
public final class VersionMatched<T> {

    /**
     * Version of the server, pattern must be like that;
     * 1_14_R1
     * 1_13_R2
     */
    @NotNull
    private final String rawVersion;

    /**
     * Classes that match.
     */
    @NotNull
    private final List<VersionClass<T>> versionClasses;

    /**
     * @param rawVersion Raw server version text
     * (i.e 1_14_R1, 1_13_R1)
     * @param versionClasses Classes which will create object
     * (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    public VersionMatched(@NotNull final String rawVersion, @NotNull final List<VersionClass<T>> versionClasses) {
        this.rawVersion = rawVersion;
        this.versionClasses = versionClasses;
    }

    /**
     * @param version Server version
     * @param versionClasses Classes which will create object
     * (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    public VersionMatched(@NotNull final Version version, @NotNull final List<VersionClass<T>> versionClasses) {
        this(version.raw(), versionClasses);
    }

    /**
     * @param rawVersion Raw server version text
     * (i.e 1_14_R1, 1_13_R1)
     * @param versionClasses Classes which will create object
     * (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    @SafeVarargs
    public VersionMatched(@NotNull final String rawVersion, @NotNull final Class<? extends T>... versionClasses) {
        this(
            rawVersion,
            Arrays.stream(versionClasses)
                .map((Function<Class<? extends T>, VersionClass<T>>) VersionClass::new)
                .collect(Collectors.toList()));
    }

    /**
     * @param versionClasses Classes which will create object
     * (i.e. Cmd1_14_R2.class, CmdRgstry1_8_R3.class)
     */
    @SafeVarargs
    public VersionMatched(@NotNull final Class<? extends T>... versionClasses) {
        this(
            new Version(),
            Arrays.stream(versionClasses)
                .map((Function<Class<? extends T>, VersionClass<T>>) VersionClass::new)
                .collect(Collectors.toList()));
    }

    /**
     * Gets instantiated class
     *
     * @param types constructor type
     * @return {@link RefConstructed}
     */
    @NotNull
    public RefConstructed<T> of(final Object... types) {
        final Class<? extends T> match = this.match();
        // noinspection unchecked
        return (RefConstructed<T>) new ClassOf<>(match).constructor(types)
            .orElseThrow(() ->
                new IllegalStateException("match() -> Couldn't find any constructor on " +
                    '"' + match.getSimpleName() + '"' + " version!"));
    }

    /**
     * Gets primitive instantiated class
     *
     * @param types constructor type
     * @return {@link RefConstructed}
     */
    @NotNull
    public RefConstructed<T> ofPrimitive(final Object... types) {
        final Class<? extends T> match = this.match();
        // noinspection unchecked
        return (RefConstructed<T>) new ClassOf<>(match).primitiveConstructor(types)
            .orElseThrow(() ->
                new IllegalStateException("match() -> Couldn't find any constructor on " +
                    '"' + match.getSimpleName() + '"' + " version!"));
    }

    /**
     * Matches classes
     *
     * @return class that match or throw exception
     */
    @NotNull
    private Class<? extends T> match() {
        return this.versionClasses.stream()
            .filter(versionClass -> versionClass.match(this.rawVersion))
            .map(VersionClass::getVersionClass)
            .findFirst()
            .orElseThrow(() ->
                new IllegalStateException("match() -> Couldn't find any matched class on " +
                    '"' + this.rawVersion + '"' + " version!"));
    }

}
