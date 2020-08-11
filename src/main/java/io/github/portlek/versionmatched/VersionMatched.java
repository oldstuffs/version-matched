/*
 * MIT License
 *
 * Copyright (c) 2020 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.portlek.versionmatched;

import io.github.portlek.bukkitversion.BukkitVersion;
import io.github.portlek.reflection.RefConstructed;
import io.github.portlek.reflection.clazz.ClassOf;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
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
    public VersionMatched(@NotNull final BukkitVersion version, @NotNull final List<VersionClass<T>> versionClasses) {
        this(version.getVersion(), versionClasses);
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
            new BukkitVersion(),
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
