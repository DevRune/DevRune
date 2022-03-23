package com.rune.hub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServersCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){

            sender.sendMessage("§cEnkel spelers kunnen dit doen");
            return true;

        }

        Player p = (Player) sender;
        p.openInventory(Main.getInstance().getMainSelector());



        return true;
    }
}
