package me.taison.wizardpractice.listeners.entity.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void handle(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
