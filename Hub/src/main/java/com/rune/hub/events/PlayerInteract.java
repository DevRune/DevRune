package com.rune.hub.events;


import com.rune.hub.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlayerInteract implements Listener {
    public PlayerInteract() {
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!player.getItemInHand().getType().equals(Material.AIR)) {
            if (player.getItemInHand().hasItemMeta()) {
                if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.getItemInHand().getItemMeta().getDisplayName().equals("§4Server selector")) {
                    player.openInventory(Main.getInstance().getMainSelector());
                    event.setCancelled(true);
                }

            }
        }
    }
}
