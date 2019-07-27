package io.github.portlek.versionmatched;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

import static org.junit.jupiter.api.Assertions.*;

class VersionClassTest {

    private static final String MC_VERSION = "1_14_R1";

    @Test
    void getVersionClass() {
        final VersionClass<ITest> versionClass = new VersionClass<>(Test1_14_R1.class);
        new Assertion<>(
            "Cannot get the class",
            versionClass.getVersionClass(),
            new IsEqual<>(Test1_14_R1.class)
        ).affirm();
    }

    @Test
    void match() {
        final VersionClass<ITest> versionClass = new VersionClass<>(Test1_14_R1.class);
        new Assertion<>(
            "Cannot get the class",
            versionClass.match(MC_VERSION),
            new IsTrue()
        ).affirm();
    }

    private interface ITest {
    }

    private class Test1_14_R1 implements ITest {
    }

    private class Test1_13_R2 implements ITest {
    }

}