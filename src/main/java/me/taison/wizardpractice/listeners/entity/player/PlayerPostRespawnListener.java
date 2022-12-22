package me.taison.wizardpractice.listeners.entity.player;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.matchmakingsystem.Duel;
import me.taison.wizardpractice.game.matchmakingsystem.Matchmaker;
import me.taison.wizardpractice.utilities.items.VariousItems;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class PlayerPostRespawnListener implements Listener {

    @EventHandler
    public void handle(PlayerPostRespawnEvent event){

        Matchmaker matchmaker = WizardPractice.getSingleton().getMatchmaker();

        User user = WizardPractice.getSingleton().getUserFactory().getByUniqueId(event.getPlayer().getUniqueId()).get();
        new BukkitRunnable() {
            @Override
            public void run() {

                if (matchmaker.getDuelByUser(user).isEmpty()) {
                    event.getPlayer().getInventory().clear();
                    event.getPlayer().getInventory().setItem(4, VariousItems.FEATHER_ITEM);
                    event.getPlayer().teleport(WizardPractice.getSingleton().getSpawnLocation());
                    return;
                }

                event.getPlayer().setGameMode(GameMode.SPECTATOR);
                event.getPlayer().teleport(matchmaker.getDuelByUser(user).get().getArena().getSpawnLocations().get(0));
            }
        }.runTaskLater(WizardPractice.getPlugin(WizardPractice.class), 5L);

    }

}
