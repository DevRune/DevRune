package com.rune.anapoliscore;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class fileaccessor {
    private static FileConfiguration File = null;
    private static File customConfigFile = null;

    public fileaccessor() {
    }

    public static void reloadFileF(String filename) {
        saveFile(filename);
        customConfigFile = new File(((main)main.getPlugin(main.class)).getDataFolder(), filename + ".yml");
        File = YamlConfiguration.loadConfiguration(customConfigFile);
        InputStream defConfigStream = ((main)main.getPlugin(main.class)).getResource(filename + ".yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(customConfigFile);
            File.setDefaults(defConfig);
        }

    }

    public static FileConfiguration getFileF(String filename) {
        if (File == null) {
            reloadFileF(filename);
        }

        return File;
    }

    public static void saveFile(String filename) {
        if (File != null && customConfigFile != null) {
            try {
                getFileF(filename).save(customConfigFile);
            } catch (IOException var2) {
                ((main)main.getPlugin(main.class)).getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, var2);
            }

        }
    }

    public static void saveXDefault(String filename) {
        Plugin plugin = main.getPlugin(main.class);
        if (customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), filename + ".yml");
        }

        if (!customConfigFile.exists()) {
            plugin.saveResource(filename + ".yml", false);
        }

        customConfigFile = null;
    }
}
