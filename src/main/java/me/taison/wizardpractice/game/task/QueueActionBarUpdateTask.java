package me.taison.wizardpractice.game.task;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.game.matchmakingsystem.queue.QueueToDuel;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.time.TimeUtil;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class QueueActionBarUpdateTask extends BukkitRunnable {

    private final WizardPractice wizard;

    private Map<GameMapType, Long> lastFoundGameTime;

    public QueueActionBarUpdateTask(WizardPractice wizardPractice){
        this.wizard = wizardPractice;

        this.lastFoundGameTime = new HashMap<>();
    }

    public void startActionBarUpdate(){
        this.runTaskTimerAsynchronously(this.wizard, 20, 35);
    }

    @Override
    public void run() {
        wizard.getMatchmaker().getQueuesToDuels().forEach(queueToDuel -> queueToDuel.getTeamsInQueue().forEach(team -> {
            Optional<QueueToDuel> queueToDuelOpt = wizard.getMatchmaker().getQueueByTeam(team);

            queueToDuelOpt.ifPresent(toDuel -> {
                team.sendActionBar("&aTrwa wyszukiwanie gry. Twój numer w kolejce: "
                        + (toDuel.getTeamsInQueue().indexOf(team) + 1)
                        + " Szacowany czas: "
                        + TimeUtil.getDurationBreakdown(this.getLastFoundGameTime(queueToDuel.getGameMapType())));
            });
        }));
    }

    public void setLastFoundGame(GameMapType gameMapType, long time){
        this.lastFoundGameTime.replace(gameMapType, time);
    }

    public long getLastFoundGameTime(GameMapType gameMapType) {
        if(this.lastFoundGameTime.get(gameMapType) == null){
            return 10000;
        }

        return lastFoundGameTime.get(gameMapType);
    }
}
