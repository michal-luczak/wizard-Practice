package me.taison.wizardpractice.data.user.impl;

import me.taison.wizardpractice.data.user.User;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class CustomInventorySettings {

    private User user;

    private Map<Integer, ItemStack> itemStackMap;
    private Map<Integer, ItemStack> armorMap;

    public CustomInventorySettings(User user) {
        this.itemStackMap = new HashMap<>();
        this.armorMap = new HashMap<>();

        this.user = user;

    }

    public boolean hasSetCustomInventory(){
        return this.itemStackMap.size() > 0;
    }

    public void saveCustomInventorySettings(PlayerInventory inv){
        IntStream.range(0, inv.getSize()).forEach(i -> {
            this.itemStackMap.put(i, inv.getContents()[i]);
        });

        IntStream.range(0, 4).forEach(i -> {
            this.armorMap.put(i, inv.getArmorContents()[i]);
        });
    }

    public void giveCustomInventory(){
        ItemStack[] contents = IntStream.range(0, 54).mapToObj(i -> this.itemStackMap.get(i)).toArray(ItemStack[]::new);
        ItemStack[] armor = IntStream.range(0, 54).mapToObj(i -> this.armorMap.get(i)).toArray(ItemStack[]::new);

        this.user.getAsPlayer().getInventory().setContents(contents);
        this.user.getAsPlayer().getInventory().setArmorContents(armor);
    }
}
