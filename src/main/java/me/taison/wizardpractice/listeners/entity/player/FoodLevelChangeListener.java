package me.taison.wizardpractice.listeners.entity.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void handle(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }
}
