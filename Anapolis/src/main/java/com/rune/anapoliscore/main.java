package com.rune.anapoliscore;


import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public final class main extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        banklocs.loadAll();
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new events(), this);
        econ.setupEconomy();
        this.saveDefaultConfig();
    }

    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("bank")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only ingame!");
                return true;
            }

            if (args.length == 0) {
                Player player = (Player)sender;
                if (!player.hasPermission("bank.create")) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to do that");
                    return true;
                }

                Block lookingAt = player.getTargetBlock((HashSet)null, 100);
                banklocs.setupBank(lookingAt.getLocation());
                player.sendMessage(ChatColor.GREEN + "Set up the bank!");
            }
        }

        return true;
    }

}
