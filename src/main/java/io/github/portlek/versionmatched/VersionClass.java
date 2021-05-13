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

import com.google.common.base.Preconditions;
import io.github.portlek.bukkitversion.BukkitVersion;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * a class that represents version classes.
 *
 * @param <T> type of the class.
 */
@RequiredArgsConstructor
final class VersionClass<T> {

  /**
   * the numbers.
   */
  private static final char[] NUMBERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

  /**
   * the raw class name.
   */
  @NotNull
  private final String rawClassName;

  /**
   * the version class.
   */
  @NotNull
  @Getter
  private final Class<? extends T> versionClass;

  /**
   * ctor.
   *
   * @param versionClass the version class.
   */
  VersionClass(@NotNull final Class<? extends T> versionClass) {
    this(versionClass.getSimpleName(), versionClass);
  }

  /**
   * matches the given version.
   *
   * @param version the version to match.
   *
   * @return {@code true} if the version matched.
   */
  boolean match(@NotNull final BukkitVersion version) {
    return this.version().equals(version);
  }

  /**
   * obtains the version.
   *
   * @return version.
   */
  @NotNull
  private BukkitVersion version() {
    final var sub = this.versionSubString();
    Preconditions.checkState(sub != -1, "version() -> Invalid name for \"%s\"", this.rawClassName);
    return new BukkitVersion(this.rawClassName.substring(sub));
  }

  /**
   * obtains the version sub string.
   *
   * @return version sub string.
   */
  private int versionSubString() {
    final var subString = new AtomicInteger();
    finalBreak:
    for (final var name : this.rawClassName.toCharArray()) {
      for (final var number : VersionClass.NUMBERS) {
        if (name == number) {
          break finalBreak;
        }
      }
      subString.incrementAndGet();
    }
    return subString.get();
  }
}
