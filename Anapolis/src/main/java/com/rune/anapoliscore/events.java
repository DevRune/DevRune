package com.rune.anapoliscore;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class events implements Listener {
    public events() {
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event) {
        System.out.println("Event fired");
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            Location loc = event.getClickedBlock().getLocation();
            if (banklocs.isBank(loc)) {
                event.setCancelled(true);
                player.openInventory(gui.getGui(player));
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getCurrentItem() != null && event.getCurrentItem().getType() != null && event.getView().getTitle() != null) {
            Inventory inv = event.getClickedInventory();
            if (event.getView().getTitle().startsWith(ChatColor.BLACK + "Bank")) {
                event.setCancelled(true);
                ItemStack stack = event.getCurrentItem();
                Player player = (Player)event.getWhoClicked();
                double price;
                int amountToBuy;
                if (event.getRawSlot() >= inv.getSize()) {
                    if (gui.prices.get(stack.getType() + "," + stack.getDurability()) == null) {
                        player.sendMessage(ChatColor.RED + "Dit item kan niet aan de bank gegeven worden!");
                    } else {
                        price = (Double)gui.prices.get(stack.getType() + "," + stack.getDurability());
                        amountToBuy = 1;
                        if (event.isLeftClick()) {
                            amountToBuy = stack.getAmount();
                        }

                        stack.setAmount(stack.getAmount() - amountToBuy);
                        player.getInventory().setItem(event.getSlot(), stack);
                        econ.economy.depositPlayer(player, (double)amountToBuy * price);
                    }
                } else if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore()) {
                    price = (Double)gui.prices.get(stack.getType() + "," + stack.getDurability());
                    amountToBuy = 1;
                    if (event.isLeftClick()) {
                        amountToBuy = stack.getMaxStackSize();
                    }

                    if (econ.economy.getBalance(player) < (double)amountToBuy * price) {
                        player.sendMessage(ChatColor.RED + "Je hebt niet genoeg geld op je bank staan hiervoor!");
                    } else if (player.getInventory().firstEmpty() == -1) {
                        player.sendMessage(ChatColor.RED + "Maak ruimte vrij in je inventory!");
                    } else {
                        econ.economy.withdrawPlayer(player, (double)amountToBuy * price);
                        ItemStack give = stack.clone();
                        give.setAmount(amountToBuy);
                        player.getInventory().addItem(new ItemStack[]{give});
                    }
                }
            }
        }
    }
}
