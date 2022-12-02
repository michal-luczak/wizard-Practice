package me.taison.wizardpractice.gui;

import me.taison.wizardpractice.gui.event.GuiItemClickEvent;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class EmptySlotFillerGuiItem extends GuiItem {

    private String name;

    public EmptySlotFillerGuiItem(String name, ItemStack itemStack) {
        super(StringUtils.color(name), itemStack);
    }

    @Override
    public ItemStack getFinalIcon(Player viewer) {
        ItemStack icon = getIcon().clone();
        ItemMeta meta = icon.getItemMeta();

        meta.setDisplayName(name);

        icon.setItemMeta(meta);
        return icon;
    }

    @Override
    public void onItemClick(GuiItemClickEvent event) {
        event.setWillGoBack(true);
    }
}
