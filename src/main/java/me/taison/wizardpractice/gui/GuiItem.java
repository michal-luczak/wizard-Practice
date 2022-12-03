package me.taison.wizardpractice.gui;

import me.taison.wizardpractice.gui.event.GuiItemClickEvent;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.apache.commons.lang3.Validate;
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
        Validate.notNull(name, "Name cannot be null!");
        Validate.notNull(icon, "Icon cannot be null!");

        this.name = StringUtils.color(name);
        this.lores = StringUtils.color(lores);

        this.icon = icon;
    }

    public GuiItem(String name, ItemStack icon, String... lores) {
        this(name, Arrays.asList(lores), icon);
    }

    private ItemStack setItemMeta(ItemStack icon) {
        ItemMeta meta = icon.getItemMeta();

        meta.setDisplayName(this.name);
        meta.setLore(this.lores);

        icon.setItemMeta(meta);
        return icon;
    }

    public ItemStack getFinalIcon(Player viewer) {
        return setItemMeta(icon.clone());
    }

    public ItemStack getIcon() {
        return icon;
    }

    protected abstract void onItemClick(GuiItemClickEvent event);
}