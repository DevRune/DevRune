package com.rune.hub.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PlayerJoin implements Listener {
    public PlayerJoin() {
    }

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().setItem(4, this.createItem(Material.COMPASS, "§4Server selector", "§7Right click to choose server!"));
        player.getInventory().setHeldItemSlot(4);
    }

    private ItemStack createItem(Material material, String name, String lore) {
        ItemStack itemstack = new ItemStack(material, 1);
        ItemMeta itemmeta = itemstack.getItemMeta();
        itemmeta.setDisplayName(name);
        itemmeta.setLore(Arrays.asList(lore));
        itemstack.setItemMeta(itemmeta);
        return itemstack;
    }
}
