package me.taison.wizardpractice.game;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DuelCounter extends BukkitRunnable {

    private final List<Team> teams;

    private final Duel duel;
    private int counter = 5;

    public DuelCounter(Duel duel, List<Team> teams) {
        this.duel = duel;
        this.teams = teams;
    }

    public void startCounting(){
        this.runTaskTimerAsynchronously(WizardPractice.getSingleton(), 0, 20);
    }

    @Override
    public void run() {
        teams.forEach(team -> team.getTeam().forEach(user ->
                user.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&aRozpocznie sie za: " + counter), 0, 20, 25)));
        if(counter < 1){
            teams.forEach(team -> team.getTeam().forEach(user ->
                    user.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&cRozpoczęty! "), 0, 20, 35)));
            this.cancel();
        }

        counter --;
    }

    public Duel getDuel() {
        return duel;
    }

    public List<Team> getTeam1() {
        return teams;
    }

}
