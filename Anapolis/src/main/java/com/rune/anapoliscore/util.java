package com.rune.anapoliscore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class util {
    public util() {
    }

    public static Location stringToAbsoluteLoc(String string) {
        string = string.replace(",", ".");
        String[] split = string.split("-");
        double xnr = Double.parseDouble(split[0].replace("q", "-"));
        double ynr = Double.parseDouble(split[1]);
        double znr = Double.parseDouble(split[2].replace("q", "-"));
        World world = (World) Bukkit.getWorlds().get(0);
        if (split.length >= 6) {
            world = Bukkit.getWorld(split[5]);
        }

        Location loc = new Location(world, xnr, ynr, znr);

        try {
            loc.setYaw((float)Double.parseDouble(split[3]));
            loc.setPitch((float)Double.parseDouble(split[4]));
        } catch (Exception var11) {
        }

        return loc;
    }

    public static String locToAbsoluteString(Location loc) {
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        String tx = String.valueOf(x);
        String tz = String.valueOf(z);
        tx = tx.replace("-", "q");
        tz = tz.replace("-", "q");
        String locstring = tx + "-" + y + "-" + tz + "-" + loc.getYaw() + "-" + loc.getPitch() + "-" + loc.getWorld().getName();
        locstring = locstring.replace(".", ",");
        return locstring;
    }

    public static String translateDouble(Double d) {
        String s = d.toString();
        s = s.replace(".", ",");
        if (s.endsWith(",0")) {
            s = s.replace(",0", "");
        } else if (s.endsWith(",00")) {
            s = s.replace(",00", "");
        }

        s = (new StringBuilder(s)).reverse().toString();
        s = s.replaceAll("(.{3})(?!$)", "$1.");
        s = (new StringBuilder(s)).reverse().toString();
        return s;
    }
}
