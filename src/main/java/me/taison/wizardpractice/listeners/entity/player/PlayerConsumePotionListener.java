package me.taison.wizardpractice.listeners.entity.player;

import me.taison.wizardpractice.utilities.items.VariousItems;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerConsumePotionListener implements Listener {

    @EventHandler
    public void handle(PlayerItemConsumeEvent event) {
        if (!event.getItem().getType().equals(Material.POTION))
            return;
        event.setReplacement(VariousItems.AIR);
    }
}
