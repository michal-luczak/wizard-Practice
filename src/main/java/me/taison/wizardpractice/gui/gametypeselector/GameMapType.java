package me.taison.wizardpractice.gui.gametypeselector;

import me.taison.wizardpractice.utilities.chat.StringUtils;
import me.taison.wizardpractice.utilities.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public enum GameMapType {

    DIAMOND_GAME(10, "&aDiament", Arrays.asList("&cRodzaj gry: Diamentowe Sety", " ", "&7Graczy w kolejce: %queuedPlayers", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.DIAMOND_SWORD, 1).addEnchant(Enchantment.KNOCKBACK, 1).toItemStack(),
            new ItemStack[]{}, new ItemStack[]{}),

    NORMAL_GAME(13, "&aNormal", Arrays.asList("&cRodzaj gry: Zwykla gra.", " ", "&7Graczy w kolejce: %queuedPlayers", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.DIAMOND_SWORD, 1).toItemStack(),
            new ItemStack[]{}, new ItemStack[]{}),

    SPEED_GAME(16, "&aSpeed", Arrays.asList("&cRodzaj gry: Speed.", " ", "&7Graczy w kolejce: %queuedPlayers", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.POTION, 1).toItemStack(),
            new ItemStack[]{}, new ItemStack[]{});

    private final int slotId;

    private final String name;
    private final List<String> description;

    private final ItemStack itemStack;



    private final ItemStack[] armor;

    private final ItemStack[] items;

    GameMapType(int slotId, String name, List<String> description, ItemStack itemStack, ItemStack[] armor, ItemStack[] items){
        this.slotId = slotId;

        this.name = name;
        this.description = description;
        this.itemStack = itemStack;
        this.armor = armor;
        this.items = items;
    }

    public int getSlotId() {
        return slotId;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getName() {
        return StringUtils.color(name);
    }

    public List<String> getDescription() {
        return StringUtils.color(description);
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public ItemStack[] getItems() {
        return items;
    }
}
