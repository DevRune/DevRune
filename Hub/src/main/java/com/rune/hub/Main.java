package com.rune.hub;

import com.rune.hub.events.InventoryClick;
import com.rune.hub.events.PlayerDropItem;
import com.rune.hub.events.PlayerInteract;
import com.rune.hub.events.PlayerJoin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public final class Main extends JavaPlugin {
    public static Main instance;
    private ConfigFile configfile;
    private Inventory inv;
    private ArrayList<SelectorData> selectorData;

    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin(){
        return plugin;
    }

    public Main() {
    }

    public void onEnable() {
        plugin = this;
        getCommand("servers").setExecutor(new ServersCommand());
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        instance = this;
        this.configfile = new ConfigFile();
        this.registerEvents();
        this.selectorData = new ArrayList();
        this.inv = Bukkit.createInventory((InventoryHolder)null, 45, "§5§lCraze§d§lMC §8>> §d§lServers");
        Iterator var2 = this.getConfigFile().getConfiguration().getKeys(false).iterator();

        while(var2.hasNext()) {
            String keys = (String)var2.next();
            String name = this.getConfigFile().getString(keys + ".NAME");
            int itemID = this.getConfigFile().getInt(keys + ".ITEM-ID");
            int slot = this.getConfigFile().getInt(keys + ".SLOT");
            String command = this.getConfigFile().getString(keys + ".SERVER");
            List<String> lore = this.getConfigFile().getStringList(keys + ".LORE");
            this.selectorData.add(new SelectorData(name, itemID, slot, command, lore));
        }

        var2 = this.selectorData.iterator();

        while(var2.hasNext()) {
            SelectorData data = (SelectorData)var2.next();
            this.inv.setItem(data.getSlot() - 1, this.createItem(data.getName(), data.getMaterialID(), data.getLore()));
        }

    }



    public void onDisable() {
    }

    public static void teleportPlayerToServer(final Player player, final String server){

        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos)
        ){

            dos.writeUTF("Connect");
            dos.writeUTF(server);
            player.sendPluginMessage(getPlugin(), "BungeeCord", baos.toByteArray());
            String message = "§5§lCraze§d§lMC §8>> &7We verbinden je nu met &f%server%";
            player.sendMessage(message.replace("%server%", server));
        } catch (final IOException e){
            e.printStackTrace();
        }
    }

    public void registerEvents() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new InventoryClick(), this);
        manager.registerEvents(new PlayerDropItem(), this);
        manager.registerEvents(new PlayerInteract(), this);
        manager.registerEvents(new PlayerJoin(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    public ConfigFile getConfigFile() {
        return this.configfile;
    }

    private ItemStack createItem(String name, int materialID, List<String> lore) {
        ItemStack itemstack = new ItemStack(Material.getMaterial(materialID), 1);
        ItemMeta itemmeta = itemstack.getItemMeta();
        itemmeta.setDisplayName(name);
        itemmeta.setLore(lore);
        itemstack.setItemMeta(itemmeta);
        return itemstack;
    }

    public Inventory getMainSelector() {
        return this.inv;
    }

    public ArrayList<SelectorData> getSelectorData() {
        return this.selectorData;
    }
}
