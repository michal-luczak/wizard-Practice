package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.UserFactory;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.DuelManager;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handle(PlayerQuitEvent event) {
        DuelManager duelManager = WizardPractice.getSingleton().getDuelManager();
        if (WizardPractice.getSingleton().getUserFactory().getUserByUniqueIdentifier(event.getPlayer().getUniqueId()).isPresent()) {
            User user = WizardPractice.getSingleton().getUserFactory().getUserByUniqueIdentifier(event.getPlayer().getUniqueId()).get();
            if (duelManager.getDuelByUser(user).isPresent())
                duelManager.stopDuel(duelManager.getDuelByUser(user).get());
        }

        UserFactory factory = WizardPractice.getSingleton().getUserFactory();

        factory.getUserByUniqueIdentifier(event.getPlayer().getUniqueId()).ifPresent(factory::unregisterUser); //TODO To też na razie jest zeby przy testowaniu nie bylo problemow

        event.setQuitMessage(StringUtils.color("&c[-] " + event.getPlayer().getName()));

        event.getPlayer().getInventory().clear();
    }
}
