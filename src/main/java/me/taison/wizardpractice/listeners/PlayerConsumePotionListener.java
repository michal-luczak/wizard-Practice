package me.taison.wizardpractice.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerConsumePotionListener implements Listener {

    @EventHandler
    public void handle(PlayerItemConsumeEvent event) {
        event.setReplacement(null);
    }
}
