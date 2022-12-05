package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.UserFactory;
import me.taison.wizardpractice.gui.gametypeselector.GameSelectorGui;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void handle(PlayerInteractEvent event) {
        if (event.getItem() == null)
            return;
        if (event.getItem().getType().equals(Material.FEATHER)) {
            event.setCancelled(true);

            new GameSelectorGui().open(event.getPlayer());
        } else if (event.getItem().getType().equals(Material.BARRIER)) {
            event.setCancelled(true);
            UserFactory userFactory = WizardPractice.getSingleton().getUserFactory();
            if (userFactory.getByUniqueId(event.getPlayer().getUniqueId()).isPresent()) {
                WizardPractice.getSingleton().getMatchmaker().removeUserFromQueue(userFactory.getByUniqueId(event.getPlayer().getUniqueId()).get());
            }
        }
    }
}
