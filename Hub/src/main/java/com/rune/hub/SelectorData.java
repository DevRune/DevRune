package com.rune.hub;

import java.util.List;

public class SelectorData {
    private String name;
    private int materialID;
    private int slot;
    private String server;
    private List<String> lore;

    public SelectorData(String name, int materialID, int slot, String server, List<String> lore) {
        this.name = name;
        this.materialID = materialID;
        this.slot = slot;
        this.server = server;
        this.lore = lore;
    }

    public String getName() {
        return this.name;
    }

    public int getMaterialID() {
        return this.materialID;
    }

    public int getSlot() {
        return this.slot;
    }

    public String getServer() {
        return this.server;
    }

    public List<String> getLore() {
        return this.lore;
    }
}
