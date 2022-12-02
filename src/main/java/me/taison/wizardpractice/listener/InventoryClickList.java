package me.taison.wizardpractice.listener;

import me.taison.wizardpractice.gui.GuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class InventoryClickList implements Listener {

    @EventHandler
    public void handle(InventoryClickEvent e){
        if (e.getWhoClicked() instanceof Player && e.getInventory().getHolder() instanceof GuiHolder) {
            e.setCancelled(true);
            ((GuiHolder) e.getInventory().getHolder()).getMenu().onInventoryClick(e);
        }
    }

}
