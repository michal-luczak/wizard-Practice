package me.taison.wizardpractice.gui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GuiHolder implements InventoryHolder {

    private final GuiMenu menu;

    public GuiHolder(GuiMenu menu) {
        this.menu = menu;
    }

    public GuiMenu getMenu() {
        return menu;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
