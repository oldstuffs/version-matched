package io.github.portlek.versionmatched;

import io.github.portlek.reflection.Reflection;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class VersionMatched<T> {

    private final Reflection reflection;
    private final List<Class<? extends T>> classes;
    private final int subString;
    private final String serverVersion;

    @SafeVarargs
    public VersionMatched(@NotNull Reflection reflection,
                          int subString,
                          @NotNull final Class<? extends T>... classes) {
        this.reflection = reflection;
        this.subString = subString;
        this.classes = Arrays.asList(classes);
        this.serverVersion = reflection.getCraftBukkitVersion().substring(1);
    }

    public T instance(Object... args) {
        return reflection.newInstance(match(), args);
    }

    private Class<? extends T> match() {
        return classes
            .stream()
            .filter(o -> o.getSimpleName().substring(subString).equals(serverVersion))
            .findFirst()
            .orElseThrow(() ->
                new RuntimeException(
                    "Unsupported version \"" + serverVersion + "\", report this to developers of core"
                )
            );
    }

}
