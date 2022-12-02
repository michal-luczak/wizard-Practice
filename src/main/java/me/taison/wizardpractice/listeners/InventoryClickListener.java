package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.gui.GuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void handle(InventoryClickEvent e) {
        e.setCancelled(true);
        if (e.getWhoClicked() instanceof Player && e.getInventory().getHolder() instanceof GuiHolder) {
            ((GuiHolder) e.getInventory().getHolder()).getMenu().onInventoryClick(e);
        }
    }

}
