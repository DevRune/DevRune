package com.rune.anapoliscore;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Iterator;

public class banklocs {
    public static ArrayList<Location> banks = new ArrayList();

    public banklocs() {
    }

    public static void loadAll() {
        fileaccessor.reloadFileF("banks");
        FileConfiguration file = fileaccessor.getFileF("banks");
        Iterator var2 = file.getConfigurationSection("").getKeys(false).iterator();

        while(var2.hasNext()) {
            String s = (String)var2.next();
            Location loc = util.stringToAbsoluteLoc(s);
            banks.add(loc);
        }

        System.out.println("Cached all banklocs");
    }

    public static void setupBank(Location loc) {
        fileaccessor.reloadFileF("banks");
        FileConfiguration file = fileaccessor.getFileF("banks");
        file.set(util.locToAbsoluteString(loc), true);
        banks.add(loc);
        System.out.println("Registered bank @ " + loc);
        fileaccessor.saveFile("banks");
    }

    public static boolean isBank(Location loc) {
        if (loc.getBlock() != null && loc.getBlock().getType() != null && loc.getBlock().getType() != Material.AIR) {
            return banks.contains(loc);
        } else {
            return false;
        }
    }
}
