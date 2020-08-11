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

import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

class VersionMatchedTest {

    private static final String MC_VERSION = "1_14_R1";

    private final VersionMatched<VersionMatchedTest.ITest> VERSION_MATCHED = new VersionMatched<>(
        VersionMatchedTest.MC_VERSION,
        VersionMatchedTest.Test1_14_R1.class,
        VersionMatchedTest.Test1_13_R2.class
    );

    @Test
    void instance() {
        new Assertion<>(
            "Cannot initiate the class",
            this.VERSION_MATCHED.of().create()
                .orElseThrow(() ->
                    new IllegalStateException("Couldn't create an object!")),
            new IsInstanceOf(VersionMatchedTest.ITest.class)
        ).affirm();
    }

    @Test
    void instancePrimitive() {
        new Assertion<>(
            "Cannot initiate the class",
            this.VERSION_MATCHED.ofPrimitive().create()
                .orElseThrow(() ->
                    new IllegalStateException("Couldn't create an object!")),
            new IsInstanceOf(VersionMatchedTest.ITest.class)
        ).affirm();
    }

    interface ITest {

    }

    private static class Test1_13_R2 implements VersionMatchedTest.ITest {

        Test1_13_R2() {
        }

    }

    private static class Test1_14_R1 implements VersionMatchedTest.ITest {

        Test1_14_R1() {
        }

    }

    private static class MckTest implements VersionMatchedTest.ITest {

        MckTest() {
        }

    }

}