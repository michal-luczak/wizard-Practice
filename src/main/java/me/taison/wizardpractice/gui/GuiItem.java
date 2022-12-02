package me.taison.wizardpractice.gui;

import me.taison.wizardpractice.gui.event.GuiItemClickEvent;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public abstract class GuiItem {

    protected final String name;

    private final List<String> lores;
    private final ItemStack icon;

    public GuiItem(String name, List<String> lores, ItemStack icon) {
        this.name = StringUtils.color(name);
        this.lores = StringUtils.color(lores);
        this.icon = icon;
    }

    public GuiItem(String name, ItemStack icon, String... lores) {
        this(name, Arrays.asList(lores), icon);
    }

    private ItemStack setItemMeta(ItemStack icon) {
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lores);
        icon.setItemMeta(meta);
        return icon;
    }

    public ItemStack getFinalIcon(Player viever) {
        return setItemMeta(icon.clone());
    }

    public ItemStack getIcon() {
        return icon;
    }

    public abstract void onItemClick(GuiItemClickEvent event);
}