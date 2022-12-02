package me.taison.wizardpractice.gui.gametypeselector;

import me.taison.wizardpractice.utilities.chat.StringUtils;
import me.taison.wizardpractice.utilities.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public enum GameMapType {

    DIAMOND_GAME("&aDiament", Collections.singletonList("&cRodzaj gry: Diamentowe Sety"), new ItemBuilder(Material.DIAMOND_SWORD, 1).addEnchant(Enchantment.KNOCKBACK, 1).toItemStack());

    private final String name;
    private final List<String> description;

    private final ItemStack itemStack;

    GameMapType(String name, List<String> description, ItemStack itemStack){
        this.name = name;
        this.description = description;
        this.itemStack = itemStack;
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

}
