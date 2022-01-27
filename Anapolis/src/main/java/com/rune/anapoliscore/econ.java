package com.rune.anapoliscore;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class econ {
    public static Economy economy = null;

    public econ() {
    }

    static boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = ((main)main.getPlugin(main.class)).getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = (Economy)economyProvider.getProvider();
        }

        return economy != null;
    }

    static void giveCash(Player player, Double amount) {
        if (amount != 0.0D) {
            economy.depositPlayer(player, amount);
        }
    }
}
