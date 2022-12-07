package me.taison.wizardpractice.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerConsumePotionListener implements Listener {

    @EventHandler
    public void handle(PlayerItemConsumeEvent event) {
        if (!event.getItem().getType().equals(Material.POTION))
            return;

        if (!event.getPlayer().getInventory().contains(Material.GLASS_BOTTLE))
            return;

        event.getPlayer().getInventory().remove(Material.GLASS_BOTTLE);
    }
}
