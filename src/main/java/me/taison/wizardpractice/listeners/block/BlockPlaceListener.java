package me.taison.wizardpractice.listeners.block;

import me.taison.wizardpractice.WizardPractice;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void handle(BlockPlaceEvent event) {
        WizardPractice.getSingleton().getUserFactory().getByUniqueId(event.getPlayer().getUniqueId()).ifPresent(user -> {
            WizardPractice.getSingleton().getMatchmaker().getDuelByUser(user).ifPresent(match -> {
                match.handleBlockPlace(event, user);
            });
        });
    }

}
