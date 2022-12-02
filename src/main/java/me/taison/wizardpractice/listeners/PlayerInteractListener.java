package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.PracticeUserFactory;
import me.taison.wizardpractice.duelsystem.queue.QueueDispatcher;
import me.taison.wizardpractice.gui.gametypeselector.GameSelectorGui;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    private final PracticeUserFactory practiceUserFactory = WizardPractice.getSingleton().getBoxUserFactory();
    private final QueueDispatcher queueDispatcher = WizardPractice.getSingleton().getQueueDispatcher();

    @EventHandler
    public void handle(PlayerInteractEvent event) {
        if (event.getItem() == null)
            return;
        if (event.getItem().getType().equals(Material.FEATHER)) {
            event.setCancelled(true);
            var practiceUser = practiceUserFactory.getUserByUniqueIdentifier(event.getPlayer().getUniqueId()).orElseThrow(IllegalStateException::new);

            new GameSelectorGui(practiceUser).open(event.getPlayer());
        } else if (event.getItem().getType().equals(Material.BARRIER)) {
            event.setCancelled(true);

            queueDispatcher.removePlayerFromQueue(event.getPlayer());
        }
    }
}
