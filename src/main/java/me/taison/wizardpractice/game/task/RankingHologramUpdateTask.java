package me.taison.wizardpractice.game.task;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class RankingHologramUpdateTask extends BukkitRunnable {


    private final WizardPractice wizard;

    public RankingHologramUpdateTask(WizardPractice wizardPractice){
        this.wizard = wizardPractice;
    }

    public void startRankingHologramUpdate(){
        this.runTaskTimerAsynchronously(this.wizard, 20, 120);
    }

    @Override
    public void run() {
        EnumSet.allOf(RankingType.class).forEach(rankingType -> {
            wizard.getHologramFactory().updateHologram(rankingType);
        });
    }
}
