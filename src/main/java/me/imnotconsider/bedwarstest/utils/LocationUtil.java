package me.imnotconsider.bedwarstest.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {
    public static Location getLocation(String string) {
        String[] rawLoc = string.split(" ");
        String world = rawLoc[0];
        double x = Double.parseDouble(rawLoc[1]);
        double y = Double.parseDouble(rawLoc[2]);
        double z = Double.parseDouble(rawLoc[3]);
        return new Location(Bukkit.getWorld(world), x, y, z);
    }
}