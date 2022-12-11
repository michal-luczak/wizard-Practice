package me.taison.wizardpractice.game.task;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.game.matchmakingsystem.queue.QueueToDuel;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Optional;

public class QueueActionBarUpdateTask extends BukkitRunnable {

    private final WizardPractice wizard;

    public QueueActionBarUpdateTask(WizardPractice wizardPractice){
        this.wizard = wizardPractice;
    }

    public void startActionBarUpdate(){
        this.runTaskTimerAsynchronously(this.wizard, 20, 35);
    }

    @Override
    public void run() {
        wizard.getMatchmaker().getQueuesToDuels().forEach(queueToDuel -> queueToDuel.getTeamsInQueue().forEach(team -> {
            Optional<QueueToDuel> queueToDuelOptional = wizard.getMatchmaker().getQueueByTeam(team);
            queueToDuelOptional.ifPresent(toDuel -> team.sendActionBar("&aTrwa wyszukiwanie gry. Twój numer w kolejce: " + (toDuel.getTeamsInQueue().indexOf(team) +1)));
        }));
    }
}
