# version-matched

### Using
```
<repository>
    <id>jcenter</id>
    <url>https://jcenter.bintray.com/</url>
</repository>

<dependency>
    <groupId>io.github.portlek</groupId>
    <artifactId>version-matched</artifactId>
    <version>2.2</version>
</dependency>
```

```java
final VersionMatched<T> matched = new VersionMatched<>(
    new MckCmdRgstry(),
    CmdRgstry1_14_R1.class,
    CmdRegistry1_13_R2.class,
    CommandRgstry1_13_R1.class,
    andSooooOnnnnnn1_12_R1.class
);

@NotNull T object = matched.of(ParametersClass.class...).instance(#ConstructorArguments);
```
