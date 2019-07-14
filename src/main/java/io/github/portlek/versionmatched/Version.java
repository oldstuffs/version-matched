package io.github.portlek.versionmatched;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version {

    private final Pattern pattern = Pattern.compile("v?(?<major>[0-9]+)[._](?<minor>[0-9]+)(?:[._](?<micro>[0-9]+))?(?<sub>.*)");
    private final Matcher matcher;

    public Version(String version) {
        this.matcher = pattern.matcher(version);
    }

    public Version() {
        this(Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1));
    }

    public int major() {
        if (matcher.matches()) {
            return Integer.parseInt(matcher.group("major"));
        }

        return 0;
    }

    public int minor() {
        if (matcher.matches()) {
            return matcher.group("minor") != null ? Integer.parseInt(matcher.group("minor")) : 0;
        }

        return 0;
    }

    public int micro() {
        if (matcher.matches()) {
            return matcher.group("micro") != null ? Integer.parseInt(matcher.group("micro")) : 0;
        }

        return 0;
    }

}
