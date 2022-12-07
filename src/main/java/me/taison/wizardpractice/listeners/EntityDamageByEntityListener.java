package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.User;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.lang.reflect.WildcardType;
import java.util.Optional;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void handle(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player player && event.getDamager() instanceof Player){
            WizardPractice.getSingleton().getUserFactory().getByUniqueId(player.getUniqueId()).ifPresent(user -> {
                Optional<User> damagerUserOptional = WizardPractice.getSingleton().getUserFactory().getByUniqueId(event.getDamager().getUniqueId());

                damagerUserOptional.ifPresent(damagerUser -> {
                    user.setLastDamager(damagerUser);
                    if (user.getTeam().equals(damagerUser.getTeam())) {
                        event.setCancelled(true);
                    }
                });
            });
        } else {
            if(event.getEntity() instanceof Player && event.getDamager() instanceof Arrow){
                Arrow arrow = (Arrow) event.getEntity();
                Player shooter = (Player) arrow.getShooter();

                if(shooter == null || !shooter.isOnline()){
                    return;
                }

                WizardPractice.getSingleton().getUserFactory().getByUniqueId(event.getEntity().getUniqueId()).ifPresent(user -> {
                    Optional<User> shooterUser = WizardPractice.getSingleton().getUserFactory().getByUniqueId(shooter.getUniqueId());
                    shooterUser.ifPresent(user::setLastDamager);
                });
            }
        }
    }

}
