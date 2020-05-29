package io.github.portlek.versionmatched;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * Gets minecraft version from
 * package version of the server.
 */
public final class Version {

    /**
     * Pattern of the server text
     * <p>
     * The pattern is like that
     * (major)_(minor)_R(micro)
     */
    @NotNull
    private static final Pattern PATTERN = Pattern.compile("v?(?<major>[0-9]+)[._](?<minor>[0-9]+)(?:[._]R(?<micro>[0-9]+))?(?<sub>.*)");

    /**
     * Server version text
     */
    @NotNull
    private final String version;

    /**
     * @param vrsn Minecraft server package name
     */
    public Version(@NotNull final String vrsn) {
        this.version = vrsn;
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
     * output is like that "(major)_(minor)_R(micro)"
     */
    @NotNull
    public String raw() {
        return this.version;
    }

    /**
     * Gets major part of the version
     *
     * @return major part
     */
    public int major() {
        return this.get("major");
    }

    /**
     * Gets minor part of the version
     *
     * @return minor part
     */
    public int minor() {
        return this.get("minor");
    }

    /**
     * Gets micro part of the version
     *
     * @return micro part
     */
    public int micro() {
        return this.get("micro");
    }

    private int get(@NotNull final String key) {
        final Matcher matcher = Version.PATTERN.matcher(this.version);
        final int number;
        if (matcher.matches()) {
            final String group = matcher.group(key);
            number = Integer.parseInt(group);
        } else {
            number = 0;
        }
        return number;
    }

}
