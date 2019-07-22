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
    <version>1.0</version>
</dependency>
```

```java
final VersionMatched<T> matched = new VersionMatched<>(
    java.util.logging.Logger,
    CmdRgstry1_14_R1.class,
    CmdRegistry1_13_R2.class,
    CommandRgstry1_13_R1.class,
    andSooooOnnnnnn1_12_R1.class
);

T object = matched.instance(#ConstructorArguments);
```