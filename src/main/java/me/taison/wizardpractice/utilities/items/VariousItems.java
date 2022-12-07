package me.taison.wizardpractice.utilities.items;

import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class VariousItems {

    //Statyczna klasa do szybkiego zarzadzania itemami

    public static final ItemStack featherItem = new ItemBuilder(Material.FEATHER).addItemFlag(ItemFlag.HIDE_ENCHANTS)
            .addEnchant(Enchantment.ARROW_DAMAGE, 1).setName(StringUtils.color("&5&lWybór duela")).
            addLoreLine(StringUtils.color("&dKliknij aby zagrać!")).toItemStack();

    public static final ItemStack barrierItem = new ItemBuilder(Material.BARRIER).addItemFlag(ItemFlag.HIDE_ENCHANTS)
            .addEnchant(Enchantment.ARROW_DAMAGE, 1).setName(StringUtils.color("&4&lAnulowanie duela")).
            addLoreLine(StringUtils.color("&cKliknij aby anulować!")).toItemStack();

}
