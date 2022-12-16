package me.taison.wizardpractice.listeners.block;

import me.taison.wizardpractice.WizardPractice;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void handle(BlockBreakEvent event){
        WizardPractice.getSingleton().getUserFactory().getByUniqueId(event.getPlayer().getUniqueId()).ifPresent(user -> {
            WizardPractice.getSingleton().getMatchmaker().getDuelByUser(user).ifPresent(match -> {
                match.handleBlockBreak(event, user);
            });
        });
    }

}
