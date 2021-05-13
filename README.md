[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

![master](https://github.com/portlek/version-matched/workflows/build/badge.svg)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.portlek/version-matched?label=version)](https://repo1.maven.org/maven2/io/github/portlek/version-matched/)

## How to Use

```xml
<dependency>
  <groupId>io.github.portlek</groupId>
  <artifactId>version-matched</artifactId>
  <version>${version}</version>
</dependency>
```

```gradle
implementation("io.github.portlek:version-mathced:${version}")
```

## Example usage

```java
final class Example {

  final VersionMatched<CommandRegistry> matched = new VersionMatched<>(
    CmdRgstry1_14_R1.class,
    CmdRegistry1_13_R2.class,
    CommandRgstry1_13_R1.class,
    andSooooOnnnnnn1_12_R1.class
  );

  @NotNull
  CommandRegistry getCommandRegistry() {
    return this.matched.of(/*Constructor Parameter's Types*/)
      .create(/*Constructor Parameters*/)
      .orElse(new NullCommandRegistry());
  }
}
```
