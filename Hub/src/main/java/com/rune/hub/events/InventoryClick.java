package com.rune.hub.events;

import java.util.Iterator;

import com.rune.hub.Main;
import com.rune.hub.SelectorData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class InventoryClick implements Listener {
    public InventoryClick() {
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (event.getInventory().getName().equals("§4§lRealityMC §8>> §d§lServers")) {
            event.setCancelled(true);
        }

        if (!event.getSlotType().equals(SlotType.OUTSIDE)) {
            if (!event.getInventory().getName().equals("§4§lRealityMC §8>> §d§lServers")) {
                event.setCancelled(true);
            } else if (!event.getCurrentItem().getType().equals(Material.AIR)) {
                if (event.getCurrentItem().hasItemMeta()) {
                    if (event.getInventory().getName().equals("§4§lRealityMC §8>> §d§lServers")) {
                        Iterator var4 = Main.getInstance().getSelectorData().iterator();

                        while(var4.hasNext()) {
                            SelectorData data = (SelectorData)var4.next();
                            if (event.getCurrentItem().getItemMeta().getDisplayName().equals(data.getName())) {
                                Main.teleportPlayerToServer(player, data.getServer());
                            }
                        }
                    }

                }
            }
        }
    }
}
