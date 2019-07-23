package io.github.portlek.versionmatched;

import org.bukkit.Bukkit;
import org.cactoos.scalar.NumberOf;
import org.cactoos.text.Replaced;
import org.cactoos.text.TextOf;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * Gets minecraft version from
 * package version of the server.
 */
public class Version {

    /**
     * Pattern of the server text
     *
     * The pattern is like that
     * <major>_<minor>_R<micro>
     */
    @NotNull
    private static final Pattern PATTERN = Pattern.compile("v?(?<major>[0-9]+)[._](?<minor>[0-9]+)(?:[._](?<micro>[0-9]+))?(?<sub>.*)");

    /**
     * Server version text
     */
    @NotNull
    private final String version;

    /**
     * @param version Minecraft server package name
     */
    public Version(@NotNull final String version) {
        this.version = version;
    }

    /**
     * Initiates with current running server package name
     */
    public Version() {
        this(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1));
    }

    /**
     * Gets raw string of the version
     *
     * @return raw string
     * @apiNote output is like that "<major>_<minor>_R<micro>"
     */
    @NotNull
    public String raw() {
        return version;
    }

    /**
     * Gets major part of the version
     *
     * @return major part
     */
    public int major() {
        return new NumberOf(
            new Replaced(
                new TextOf(
                    version
                ),
                () -> PATTERN,
                matcher -> matcher.group("major")
            )
        ).intValue();
    }

    /**
     * Gets minor part of the version
     *
     * @return minor part
     */
    public int minor() {
        return new NumberOf(
            new Replaced(
                new TextOf(version),
                () -> PATTERN,
                matcher -> matcher.group("minor")
            )
        ).intValue();
    }

    /**
     * Gets micro part of the version
     *
     * @return micro part
     */
    public int micro() {
        return new NumberOf(
            new Replaced(
                new TextOf(version),
                () -> PATTERN,
                matcher -> matcher.group("micro")
            )
        ).intValue();
    }

}
