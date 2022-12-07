package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.matchmakingsystem.Duel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Optional;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void handle(PlayerDeathEvent event) {
        event.deathMessage(null);

        if(event.getEntity().getKiller() == null) {
            return;
        }

        Optional<User> victimUser = WizardPractice.getSingleton().getUserFactory().getByUniqueId(event.getEntity().getUniqueId());
        Optional<User> killerUser = WizardPractice.getSingleton().getUserFactory().getByUniqueId(event.getEntity().getKiller().getUniqueId());

        if(killerUser.isPresent() && victimUser.isPresent()){
            Optional<Duel> userDuel = WizardPractice.getSingleton().getMatchmaker().getDuelByUser(victimUser.get());

            userDuel.ifPresent(duel -> {
                duel.handleDeath(killerUser.get(), victimUser.get());
            });
        }
    }

}
