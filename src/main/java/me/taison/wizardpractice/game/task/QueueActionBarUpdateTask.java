package me.taison.wizardpractice.game.task;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.game.matchmakingsystem.queue.QueueToDuel;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import me.taison.wizardpractice.utilities.random.RandomUtils;
import me.taison.wizardpractice.utilities.time.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class QueueActionBarUpdateTask extends BukkitRunnable {

    private final WizardPractice wizard;

    private final Map<GameMapType, Long> lastFoundGameTime;

    public QueueActionBarUpdateTask(WizardPractice wizardPractice){
        this.wizard = wizardPractice;

        this.lastFoundGameTime = new HashMap<>();
    }

    public void startActionBarUpdate(){
        this.runTaskTimerAsynchronously(this.wizard, 20, 40);
    }

    @Override
    public void run() {
        wizard.getMatchmaker().getQueuesToDuels().forEach(queueToDuel -> queueToDuel.getTeamsInQueue().forEach(team -> {
            Optional<QueueToDuel> queueToDuelOpt = wizard.getMatchmaker().getQueueByTeam(team);

            queueToDuelOpt.ifPresent(toDuel -> {
                long estimatedWaitTime = 7500;
                long teamWaitTime = System.currentTimeMillis() - team.getWaitingTime(queueToDuel.getGameMapType());

                if (this.lastFoundGameTime.get(queueToDuel.getGameMapType()) != null) {
                    estimatedWaitTime = this.lastFoundGameTime.get(queueToDuel.getGameMapType()) / toDuel.getTeamsInQueue().size();
                }

                if(teamWaitTime > estimatedWaitTime){
                    estimatedWaitTime = teamWaitTime + 1100;
                }

                if(teamWaitTime / 1000 % 30 == 0){
                    Bukkit.broadcastMessage(StringUtils.color("&eTeam - " + team.getLeader().getName() + " poszukuje przeciwnika/ów do trybu: " + toDuel.getGameMapType().getName()));
                }

                team.sendActionBar(String.format("&eTrwa wyszukiwanie gry. &e&l| &eTwój numer w kolejce: %d &e&l| &eSzacowany czas: %s &e&l| &eAktualny czas oczekiwania: %s",
                        toDuel.getTeamsInQueue().indexOf(team) + 1,
                        TimeUtil.getDurationBreakdown(estimatedWaitTime),
                        TimeUtil.getDurationBreakdown(teamWaitTime)));
            });
        }));
    }

    public void setLastFoundGame(GameMapType gameMapType, long time){
        this.lastFoundGameTime.put(gameMapType, time);
    }
}
