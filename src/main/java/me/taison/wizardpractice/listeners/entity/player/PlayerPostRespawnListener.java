package me.taison.wizardpractice.listeners.entity.player;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.utilities.items.VariousItems;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PlayerPostRespawnListener implements Listener {

    @EventHandler
    public void handle(PlayerPostRespawnEvent event){
        event.getPlayer().teleport(WizardPractice.getSingleton().getSpawnLocation());
        new BukkitRunnable() {
            @Override
            public void run() {
                event.getPlayer().getInventory().clear();
                event.getPlayer().getInventory().setItem(4, VariousItems.FEATHER_ITEM);
            }

        }.runTaskLater(WizardPractice.getPlugin(WizardPractice.class), 1L);

        //TODO potem przekazywanie do areny  w celu dodania do spectatorów... duel.playerRespawn(user);
    }

}
