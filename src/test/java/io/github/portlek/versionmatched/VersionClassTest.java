package io.github.portlek.versionmatched;

import io.github.portlek.bukkitversion.BukkitVersion;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Assertion;
import org.llorllale.cactoos.matchers.IsTrue;

final class VersionClassTest {

  private static final BukkitVersion MC_VERSION = new BukkitVersion("1_14_R1");

  @Test
  void getVersionClass() {
    final var versionClass = new VersionClass<>(VersionClassTest.Test1_14_R1.class);
    new Assertion<>(
      "Cannot get the class",
      versionClass.getVersionClass(),
      new IsEqual<>(VersionClassTest.Test1_14_R1.class)
    ).affirm();
  }

  @Test
  void match() {
    final var versionClass = new VersionClass<>(VersionClassTest.Test1_14_R1.class);
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
