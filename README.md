# version-matched

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
