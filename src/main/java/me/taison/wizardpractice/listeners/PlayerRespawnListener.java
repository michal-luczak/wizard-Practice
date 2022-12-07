package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void handle(PlayerRespawnEvent event){
        event.getPlayer().teleport(WizardPractice.getSingleton().getSpawnLocation());
        //TODO potem przekazywanie do areny  w celu dodania do spectatorów... duel.playerRespawn(user);
    }

}
