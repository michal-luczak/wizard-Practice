package me.taison.wizardpractice.game;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.impl.TeamImpl;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DuelCounter extends BukkitRunnable {

    private final Team team1;
    private final Team team2;

    private final Duel duel;
    private int counter = 5;

    public DuelCounter(Duel duel, Team team1, Team team2) {
        this.duel = duel;
        this.team1 = team1;
        this.team2 = team2;
    }

    public void startCounting(){
        this.runTaskTimerAsynchronously(WizardPractice.getSingleton(), 0, 20);
    }

    @Override
    public void run() {
        team1.getTeam().forEach(user ->
                user.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&aRozpocznie sie za: " + counter), 0, 20, 25));
        team2.getTeam().forEach(user ->
                user.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&aRozpocznie sie za: " + counter), 0, 20, 25));
        if(counter < 1){
            team1.getTeam().forEach(user ->
                    user.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&cRozpoczęty! "), 0, 20, 35));
            team2.getTeam().forEach(user ->
                    user.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&cRozpoczęty! "), 0, 20, 35));
            this.cancel();
        }

        counter --;
    }

    public Duel getDuel() {
        return duel;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }
}
