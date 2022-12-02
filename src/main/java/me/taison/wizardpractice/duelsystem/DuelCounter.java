package me.taison.wizardpractice.duelsystem;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DuelCounter extends BukkitRunnable {

    private final Player player1;
    private final Player player2;
    private byte counter = 3;

    public DuelCounter(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {

        //TODO player1.sendTitle(3, 2, 1... GO);
        //TODO player2.sendTitle(3, 2, 1... GO);

        counter --;
    }
}
