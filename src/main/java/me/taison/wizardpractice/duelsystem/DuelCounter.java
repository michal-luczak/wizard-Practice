package me.taison.wizardpractice.duelsystem;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DuelCounter extends BukkitRunnable {

    private final Player player1;
    private final Player player2;

    private final Duel duel;
    private int counter = 10;

    public DuelCounter(Duel duel, Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.duel = duel;
    }

    public void startCounting(){
        this.runTaskTimerAsynchronously(WizardPractice.getSingleton(), 0, 20);
    }

    @Override
    public void run() {
        player1.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&aRozpocznie sie za: " + counter), 0, 20, 25);
        player2.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&aRozpocznie sie za: " + counter), 0, 20, 25);
        if(counter < 1){
            player1.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&cRozpoczęty! "), 0, 20, 160);
            player2.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&cRozpoczęty! "), 0, 20, 160);

            this.cancel();
        }

        counter --;

    }

    public Duel getDuel() {
        return duel;
    }
}
