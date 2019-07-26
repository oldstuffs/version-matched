package io.github.portlek.versionmatched;

import org.hamcrest.core.IsInstanceOf;
import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

class VersionMatchedTest {

    private static final String MC_VERSION = "1_14_R1";
    private final VersionMatched<ITest> VERSION_MATCHED = new VersionMatched<>(
        MC_VERSION,
        Test1_14_R1.class,
        Test1_13_R2.class
    );

    @Test
    void instance() {
        new Assertion<>(
            "Cannot initiate the class",
            VERSION_MATCHED.instance(),
            new IsInstanceOf(ITest.class)
        ).affirm();
    }

    @Test
    void instancePrimitive() {
        new Assertion<>(
            "Cannot initiate the class",
            VERSION_MATCHED.instancePrimitive(),
            new IsNot<>(
                new IsNull<>()
            )
        ).affirm();
    }

}