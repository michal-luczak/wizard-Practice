package me.taison.wizardpractice.game.matchmakingsystem.duel;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class DuelCounter extends BukkitRunnable {

    private final List<Team> teams;

    private final Duel duelImpl;
    private int counter = 5;



    public DuelCounter(Duel duelImpl, List<Team> teams) {
        this.duelImpl = duelImpl;
        this.teams = teams;
    }



    @Override
    public void run() {

        teams.forEach(team ->
                team.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&aRozpocznie sie za: " + counter), 0, 20, 25));
        if(counter < 1){
            teams.forEach(team ->
                    team.sendTitle(StringUtils.color("&6Pojedynek"), StringUtils.color("&cRozpoczęty! "), 0, 20, 35));
            this.cancel();
        }

        counter --;
    }

    public void startCounting(){
        this.runTaskTimerAsynchronously(WizardPractice.getSingleton(), 0, 20);
    }



    //      GETTERS      \\
    public Duel getDuel() {
        return duelImpl;
    }
    public List<Team> getTeam() {
        return teams;
    }

}
