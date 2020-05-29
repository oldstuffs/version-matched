# version-matched

[![idea](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)
[![rultor](https://www.rultor.com/b/yegor256/rultor)](https://www.rultor.com/p/portlek/version-matched)

[![Build Status](https://travis-ci.com/portlek/version-matched.svg?branch=master)](https://travis-ci.com/portlek/version-matched)
![Maven Central](https://img.shields.io/maven-central/v/io.github.portlek/version-matched?label=version)

### Using
```gradle
implementation("io.github.portlek:version-mathced:2.5")
```
```xml
<dependency>
    <groupId>io.github.portlek</groupId>
    <artifactId>version-matched</artifactId>
    <version>${version}</version>
</dependency>
```

```java
final VersionMatched<CommandRegistry> matched = new VersionMatched<>(
    CmdRgstry1_14_R1.class,
    CmdRegistry1_13_R2.class,
    CommandRgstry1_13_R1.class,
    andSooooOnnnnnn1_12_R1.class
);

this.matched.of(/*Constructor Parameter's Types*/)
  .create(/*Constructor Parameters*/)
  .ifPresent(registry -> {
    System.out.println("A match found!");
  });
```
