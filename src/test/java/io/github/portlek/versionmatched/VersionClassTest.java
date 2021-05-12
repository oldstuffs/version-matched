package io.github.portlek.versionmatched;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

final class VersionClassTest {

  private static final String MC_VERSION = "1_14_R1";

  @Test
  void getVersionClass() {
    final VersionClass<VersionClassTest.ITest> versionClass = new VersionClass<>(VersionClassTest.Test1_14_R1.class);
    new Assertion<>(
      "Cannot get the class",
      versionClass.getVersionClass(),
      new IsEqual<>(VersionClassTest.Test1_14_R1.class)
    ).affirm();
  }

  @Test
  void match() {
    final VersionClass<VersionClassTest.ITest> versionClass = new VersionClass<>(VersionClassTest.Test1_14_R1.class);
    new Assertion<>(
      "Cannot get the class",
      versionClass.match(VersionClassTest.MC_VERSION),
      new IsTrue()
    ).affirm();
  }

  private interface ITest {

  }

  private static class Test1_13_R2 implements VersionClassTest.ITest {

  }

  private static class Test1_14_R1 implements VersionClassTest.ITest {

  }
}
