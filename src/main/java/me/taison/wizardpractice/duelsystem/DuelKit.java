package me.taison.wizardpractice.duelsystem;

import org.bukkit.inventory.ItemStack;

public enum DuelKit {

    DIAMOND(new ItemStack[]{}, new ItemStack[]{}),
    DIAMOND2(new ItemStack[]{}, new ItemStack[]{}),
    IRON(new ItemStack[]{}, new ItemStack[]{});

    private final ItemStack[] items;
    private final ItemStack[] armor;

    DuelKit(ItemStack[] items, ItemStack[] armor) {
        this.items = items;
        this.armor = armor;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public ItemStack[] getArmor() {
        return armor;
    }
}
