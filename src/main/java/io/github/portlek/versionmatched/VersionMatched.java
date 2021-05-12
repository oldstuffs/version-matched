/*
 * MIT License
 *
 * Copyright (c) 2021 Hasan Demirtaş
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
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * matches classes with your server version and choose the right class for instantiating instead of you.
 *
 * @param <T> the interface of classes.
 */
@RequiredArgsConstructor
public final class VersionMatched<T> {

  /**
   * version of the server, pattern must be like that;
   * 1_14_R1
   * 1_13_R2
   */
  @NotNull
  private final String rawVersion;

  /**
   * classes that match.
   */
  @NotNull
  private final List<VersionClass<T>> versionClasses;

  /**
   * ctor.
   *
   * @param version the version.
   * @param versionClasses the version classes.
   */
  public VersionMatched(@NotNull final BukkitVersion version, @NotNull final List<VersionClass<T>> versionClasses) {
    this(version.getVersion(), versionClasses);
  }

  /**
   * ctor.
   *
   * @param rawVersion the raw version.
   * @param versionClasses the version classes.
   */
  @SafeVarargs
  public VersionMatched(@NotNull final String rawVersion, @NotNull final Class<? extends T>... versionClasses) {
    this(rawVersion,
      Arrays.stream(versionClasses)
        .map((Function<Class<? extends T>, VersionClass<T>>) VersionClass::new)
        .collect(Collectors.toList()));
  }

  /**
   * ctor.
   *
   * @param versionClasses the version classes.
   */
  @SafeVarargs
  public VersionMatched(@NotNull final Class<? extends T>... versionClasses) {
    this(new BukkitVersion(),
      Arrays.stream(versionClasses)
        .map((Function<Class<? extends T>, VersionClass<T>>) VersionClass::new)
        .collect(Collectors.toList()));
  }

  /**
   * gets instantiated class.
   *
   * @param types the types to get.
   *
   * @return constructor of class of {@link T}.
   */
  @NotNull
  public RefConstructed<T> of(@NotNull final Object... types) {
    final var match = this.match();
    // noinspection unchecked
    return (RefConstructed<T>) new ClassOf<>(match).getConstructor(types)
      .orElseThrow(() ->
        new IllegalStateException(String.format("match() -> Couldn't find any constructor on \"%s\" version!",
          match.getSimpleName())));
  }

  /**
   * gets instantiated class.
   *
   * @param types the types to get.
   *
   * @return constructor of class of {@link T}.
   */
  @NotNull
  public RefConstructed<T> ofPrimitive(@NotNull final Object... types) {
    final var match = this.match();
    // noinspection unchecked
    return (RefConstructed<T>) new ClassOf<>(match).getPrimitiveConstructor(types)
      .orElseThrow(() ->
        new IllegalStateException("match() -> Couldn't find any constructor on " +
          '"' + match.getSimpleName() + '"' + " version!"));
  }

  /**
   * matches classes.
   *
   * @return class that match or throw exception.
   */
  @NotNull
  private Class<? extends T> match() {
    return this.versionClasses.stream()
      .filter(versionClass -> versionClass.match(this.rawVersion))
      .map(VersionClass::getVersionClass)
      .findFirst()
      .orElseThrow(() ->
        new IllegalStateException(String.format("match() -> Couldn't find any matched class on \"%s\" version!",
          this.rawVersion)));
  }
}
