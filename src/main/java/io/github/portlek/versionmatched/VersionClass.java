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
