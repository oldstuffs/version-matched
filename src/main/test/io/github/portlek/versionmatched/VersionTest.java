package io.github.portlek.versionmatched;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest {

    private static final String MC_VERSION = "1_14_R1";
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

    }

    @Test
    void minor() {

    }

    @Test
    void micro() {

    }

}