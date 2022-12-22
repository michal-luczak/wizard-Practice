package me.taison.wizardpractice.listeners.entity;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.utilities.world.WorldUtils;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Optional;

public class EntityExplodeListener implements Listener {

    @EventHandler
    public void handle(EntityExplodeEvent event) {
        event.setCancelled(true);

        if(event.getEntity() instanceof Fireball fireball){
            Player shooter = (Player) fireball.getShooter();

            if(shooter == null || !shooter.isOnline()){
                return;
            }

            Optional<User> shooterUser = WizardPractice.getSingleton().getUserFactory().getByUniqueId(shooter.getUniqueId());
            shooterUser.ifPresent(user -> WorldUtils.getNearby(event.getLocation(), 4).forEach(player -> {
                WizardPractice.getSingleton().getUserFactory().getByUniqueId(player.getUniqueId()).ifPresent(playerUser -> {
                    playerUser.setLastDamager(user);
                });
            }));
        }
    }

}
