package io.github.portlek.versionmatched;

import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;

final class VersionMatchedTest {

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

  private static class MckTest implements VersionMatchedTest.ITest {

    MckTest() {
    }
  }

  private static class Test1_13_R2 implements VersionMatchedTest.ITest {

    Test1_13_R2() {
    }
  }

  private static class Test1_14_R1 implements VersionMatchedTest.ITest {

    Test1_14_R1() {
    }
  }
}
