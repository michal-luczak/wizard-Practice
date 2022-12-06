package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.UserFactory;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.matchmakingsystem.Matchmaker;
import me.taison.wizardpractice.game.matchmakingsystem.Matchmakerimpl;
import me.taison.wizardpractice.gui.GuiHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void handle(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player))
            return;

        Matchmaker matchmaker = WizardPractice.getSingleton().getMatchmaker();
        UserFactory userFactory = WizardPractice.getSingleton().getUserFactory();

        if (userFactory.getByUniqueId(e.getWhoClicked().getUniqueId()).isEmpty()) {
            return;
        }

        User user = userFactory.getByUniqueId(e.getWhoClicked().getUniqueId()).get();

        if (matchmaker.getDuelByUser(user).isEmpty() && !e.getWhoClicked().isOp()) {
            e.setCancelled(true);
        }

        if (e.getInventory().getHolder() instanceof GuiHolder) {
            e.setCancelled(true);
            ((GuiHolder) e.getInventory().getHolder()).getMenu().onInventoryClick(e);
        }
    }

}
