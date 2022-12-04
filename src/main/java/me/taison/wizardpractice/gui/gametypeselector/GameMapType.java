package me.taison.wizardpractice.gui.gametypeselector;

import me.taison.wizardpractice.utilities.chat.StringUtils;
import me.taison.wizardpractice.utilities.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public enum GameMapType {

    DIAMOND_GAME(10, "&aDiament", Arrays.asList("&cRodzaj gry: Diamentowe Sety", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.DIAMOND_SWORD, 1).addEnchant(Enchantment.KNOCKBACK, 1).toItemStack(),
            new ItemStack[]{}, new ItemStack[]{}, 2),

    NORMAL_GAME(13, "&aNormal", Arrays.asList("&cRodzaj gry: Zwykla gra.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.DIAMOND_SWORD, 1).toItemStack(),
            new ItemStack[]{}, new ItemStack[]{}, 2),

    SPEED_GAME(16, "&aSpeed", Arrays.asList("&cRodzaj gry: Speed.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.POTION, 1).toItemStack(),
            new ItemStack[]{}, new ItemStack[]{}, 2),

    DIAMOND_GAME_MUTLI_TEAM(20, "&aDiament XvsXvsX", Arrays.asList("&cRodzaj gry: Diamentowe Sety", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.DIAMOND_SWORD, 1).addEnchant(Enchantment.KNOCKBACK, 1).toItemStack(),
            new ItemStack[]{}, new ItemStack[]{}, 3),

    NORMAL_GAME_MUTLI_TEAM(22, "&aNormal XvsXvsX", Arrays.asList("&cRodzaj gry: Zwykla gra.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.DIAMOND_SWORD, 1).toItemStack(),
            new ItemStack[]{}, new ItemStack[]{}, 3),

    SPEED_GAME_MUTLI_TEAM(24, "&aSpeed XvsXvsX", Arrays.asList("&cRodzaj gry: Speed.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.POTION, 1).toItemStack(),
            new ItemStack[]{}, new ItemStack[]{}, 3);

    private final int slotId;

    private final String name;
    private final List<String> description;

    private final ItemStack itemStack;

    private final ItemStack[] armor;

    private final ItemStack[] items;

    private final int slots;

    GameMapType(int slotId, String name, List<String> description, ItemStack itemStack, ItemStack[] armor, ItemStack[] items, int slots){
        this.slotId = slotId;

        this.name = name;
        this.description = description;
        this.itemStack = itemStack;
        this.armor = armor;
        this.items = items;
        this.slots = slots;
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

    public int getSlots() {
        return slots;
    }
}
