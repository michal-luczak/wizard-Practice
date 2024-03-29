package me.taison.wizardpractice.listeners.entity.player;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.TeamFactory;
import me.taison.wizardpractice.data.factory.UserFactory;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.matchmakingsystem.Matchmaker;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handle(PlayerQuitEvent event) {
        UserFactory userFactory = WizardPractice.getSingleton().getUserFactory();
        TeamFactory teamFactory = WizardPractice.getSingleton().getTeamFactory();

        Matchmaker matchmaker = WizardPractice.getSingleton().getMatchmaker();

        if (userFactory.getByUniqueId(event.getPlayer().getUniqueId()).isPresent()) {
            User user = userFactory.getByUniqueId(event.getPlayer().getUniqueId()).get();

            matchmaker.getDuelByUser(user).ifPresent(action -> {
                action.playerLeft(user);
            });

            matchmaker.getQueueByTeam(user.getTeam()).ifPresent(action -> {
                matchmaker.removeTeamFromQueue(user.getTeam());

                user.getTeam().sendTitle("&6&lSYSTEM DOBIERANIA", "&cGracz z twojej druzyny wyszedl z gry, zatrzymano dobieranie.", 0, 0, 120);
            });

        }

        userFactory.getByUniqueId(event.getPlayer().getUniqueId()).ifPresent(user -> {
            teamFactory.unregister(user.getTeam());
        });

        event.quitMessage(Component.text(StringUtils.color("&c[-] " + event.getPlayer().getName())));
    }
}
