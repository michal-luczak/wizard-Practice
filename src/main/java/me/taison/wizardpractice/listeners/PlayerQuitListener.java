package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.TeamFactory;
import me.taison.wizardpractice.data.factory.UserFactory;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.DuelManager;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handle(PlayerQuitEvent event) {
        UserFactory userFactory = WizardPractice.getSingleton().getUserFactory();
        TeamFactory teamFactory = WizardPractice.getSingleton().getTeamFactory();

        DuelManager duelManager = WizardPractice.getSingleton().getDuelManager();

        if (userFactory.getByUniqueId(event.getPlayer().getUniqueId()).isPresent()) {
            User user = userFactory.getByUniqueId(event.getPlayer().getUniqueId()).get();

            duelManager.getDuelByUser(user).ifPresent(duelManager::stopDuel);

        }

        userFactory.getByUniqueId(event.getPlayer().getUniqueId()).ifPresent(user -> {
            teamFactory.unregister(user.getTeam());

            userFactory.unregisterUser(user);

        });

        event.quitMessage(Component.text(StringUtils.color("&c[-] " + event.getPlayer().getName())));
    }
}
