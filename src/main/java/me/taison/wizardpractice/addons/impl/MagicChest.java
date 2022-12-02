package me.taison.wizardpractice.addons.impl;

import org.bukkit.inventory.ItemStack;

public class MagicChest {

    private final ItemStack item;

    private final double probability;

    private final String message;

    public MagicChest(ItemStack item, double probability, String message){
        this.item = item;

        this.probability = probability;

        this.message = message;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getProbability() {
        return probability;
    }

    public String getMessage() {
        return message;
    }

}
