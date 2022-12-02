package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.PracticeUserFactory;
import me.taison.wizardpractice.duelsystem.DuelManager;
import me.taison.wizardpractice.utilities.chat.StringUtils;
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

        PracticeUserFactory factory = WizardPractice.getSingleton().getBoxUserFactory();

        factory.getUserByUniqueIdentifier(event.getPlayer().getUniqueId()).ifPresent(factory::unregisterUser); //TODO To też na razie jest zeby przy testowaniu nie bylo problemow

        event.setQuitMessage(StringUtils.color("&c[-] " + event.getPlayer().getName()));

        event.getPlayer().getInventory().clear();
    }
}
