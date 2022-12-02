package me.taison.wizardpractice.listener;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.duelsystem.DuelManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handle(PlayerQuitEvent event) {
        DuelManager duelManager = WizardPractice.getSingleton().getDuelManager();
        if (duelManager.getDuelByPlayer(event.getPlayer()).isPresent()) {
            duelManager.stopDuel(duelManager.getDuelByPlayer(event.getPlayer()).get());
        }
    }
}
