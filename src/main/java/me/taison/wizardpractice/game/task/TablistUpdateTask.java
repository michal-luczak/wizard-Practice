package me.taison.wizardpractice.game.task;

import me.taison.wizardpractice.WizardPractice;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TablistUpdateTask extends BukkitRunnable {

    private final WizardPractice wizardPractice;

    public TablistUpdateTask(WizardPractice wizardPractice){
        this.wizardPractice = wizardPractice;
    }

    public void startTablistUpdate(){
        this.runTaskTimerAsynchronously(wizardPractice, 20, 120);
    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            wizardPractice.getUserFactory().getByUniqueId(player.getUniqueId()).ifPresent(user -> {
                user.getTablist().send();
            });
        });
    }
}
