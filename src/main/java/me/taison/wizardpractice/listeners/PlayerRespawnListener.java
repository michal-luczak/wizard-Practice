package me.taison.wizardpractice.listeners;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import me.taison.wizardpractice.WizardPractice;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void handle(PlayerPostRespawnEvent event){
        event.getPlayer().teleport(WizardPractice.getSingleton().getSpawnLocation());

        //TODO potem przekazywanie do areny  w celu dodania do spectatorów... duel.playerRespawn(user);
    }

}
