package io.github.portlek.versionmatched;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

class VersionTest {

    private static final String MC_VERSION = "1_8_R3";

    private static final Version VERSION = new Version(VersionTest.MC_VERSION);

    @Test
    void raw() {
        new Assertion<>(
            "Cannot get the raw version",
            VersionTest.VERSION.raw(),
            new IsEqual<>(VersionTest.MC_VERSION)
        ).affirm();
    }

    @Test
    void major() {
        new Assertion<>(
            "Cannot get the major  version",
            VersionTest.VERSION.major(),
            new IsEqual<>(1)
        ).affirm();
    }

    @Test
    void minor() {
        new Assertion<>(
            "Cannot get the minor version",
            VersionTest.VERSION.minor(),
            new IsEqual<>(8)
        ).affirm();
    }

    @Test
    void micro() {
        new Assertion<>(
            "Cannot get the micro version",
            VersionTest.VERSION.micro(),
            new IsEqual<>(3)
        ).affirm();
    }

}