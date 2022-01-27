package com.rune.anapoliscore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class gui {
    public static HashMap<String, Double> prices = new HashMap();
    public static HashMap<Integer, String> slotNrs = new HashMap();

    public gui() {
    }

    public static Inventory getGui(Player player) {
        Inventory inv = Bukkit.createInventory((InventoryHolder)null, 36, ChatColor.BLACK + "Bank");
        int id;
        int sub;
        double price;
        ItemStack stack;
        ItemMeta meta;
        ArrayList lore;
        if (!slotNrs.isEmpty()) {
            Iterator var14 = slotNrs.keySet().iterator();

            while(var14.hasNext()) {
                int slot = (Integer)var14.next();
                String itemstring = (String)slotNrs.get(slot);
                String[] split = itemstring.split(",");
                id = Integer.parseInt(split[0]);
                sub = Integer.parseInt(split[1]);
                price = (Double)prices.get(itemstring);
                stack = new ItemStack(Material.getMaterial(split[0]), 1, (short)sub);
                meta = stack.getItemMeta();
                lore = new ArrayList();
                lore.add(ChatColor.GOLD + "Waarde: €" + util.translateDouble(price));
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.GOLD + "€" + util.translateDouble(price));
                stack.setItemMeta(meta);
                inv.setItem(slot, stack);
            }

            return inv;
        } else {
            fileaccessor.reloadFileF("config");
            FileConfiguration f = fileaccessor.getFileF("config");
            Iterator var4 = f.getConfigurationSection("").getKeys(false).iterator();

            while(var4.hasNext()) {
                String s = (String)var4.next();
                int slot = Integer.parseInt(s);
                id = f.getInt(s + ".item.id");
                sub = f.getInt(s + ".item.sub");
                price = f.getDouble(s + ".price");
                prices.put(id + "," + sub, price);
                stack = new ItemStack(Material.getMaterial(f.getString(s + ".item.id")), 1, (short)sub);
                meta = stack.getItemMeta();
                lore = new ArrayList();
                lore.add(ChatColor.GOLD + "Waarde: €" + util.translateDouble(price));
                meta.setLore(lore);
                meta.setDisplayName(ChatColor.GOLD + "€" + util.translateDouble(price));
                stack.setItemMeta(meta);
                inv.setItem(slot, stack);
            }

            return inv;
        }
    }
}
