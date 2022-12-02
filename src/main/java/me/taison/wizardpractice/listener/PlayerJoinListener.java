package me.taison.wizardpractice.listener;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.PracticeUserFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handle(PlayerJoinEvent e){
        PracticeUserFactory factory = WizardPractice.getSingleton().getBoxUserFactory();

        factory.getUserByUniqueIdentifier(e.getPlayer().getUniqueId()).ifPresent(user -> {

        });
    }

}
