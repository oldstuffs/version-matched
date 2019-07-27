package io.github.portlek.versionmatched;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {

    private static final String MC_VERSION = "1_8_R3";
    private static final Version VERSION = new Version(MC_VERSION);

    @Test
    void raw() {
        new Assertion<>(
            "Cannot get the raw version",
            VERSION.raw(),
            new IsEqual<>(MC_VERSION)
        ).affirm();
    }

    @Test
    void major() {
        new Assertion<>(
            "Cannot get the raw version",
            VERSION.major(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void minor() {
        new Assertion<>(
            "Cannot get the raw version",
            VERSION.minor(),
            new IsEqual<>(8)
        ).affirm();
    }

    @Test
    void micro() {
        new Assertion<>(
            "Cannot get the raw version",
            VERSION.micro(),
            new IsEqual<>(3)
        ).affirm();
    }

}