package me.taison.wizardpractice.data.factory.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.HologramFactory;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.AbstractRanking;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class HologramFactoryImpl implements HologramFactory {

    private final List<String> holograms;

    private final WizardPractice wizard;

    public HologramFactoryImpl(WizardPractice wizard){
        this.holograms = new ArrayList<>();

        this.wizard = wizard;

        this.prepareRankingHolograms();
    }

    private void prepareRankingHolograms(){
        EnumSet.allOf(RankingType.class).forEach(rankingType -> {
            this.addHologram(rankingType.name(), rankingType.getHologramLocation());
            this.updateHologram(rankingType);
        });

        WizardPractice.getSingleton().getLogger().info("Stworzono hologramy rankingowe.");
    }


    @Override
    public void addHologram(String name, Location location) {
        DHAPI.createHologram(name, location);
    }

    @Override
    public void updateHologram(String hologramName) {
        //TODO normalne statyczne lub inne hologramy na pozniej ?
    }

    @Override
    public void updateHologram(RankingType rankingType) {
        Hologram hologram = DHAPI.getHologram(rankingType.name());

        DHAPI.setHologramLines(hologram, this.getTextFor(rankingType));
    }

    @Override
    public List<String> findAll() {
        return this.holograms;
    }

    private List<String> getTextFor(RankingType rankingType){
        List<User> topUsers = wizard.getRankingFactory().getTopUsers(10, rankingType);

        List<String> messageLines = new ArrayList<>();
        messageLines.add(StringUtils.color("&7Lista najlepszych w Rankingu "));
        messageLines.add(rankingType.getReadableName());
        messageLines.add("");
        for (int i = 0; i < 10; i++) {
            if (i >= topUsers.size()) {
                messageLines.add(StringUtils.color("&a" + (i + 1) + ". Brak rankingu."));
            } else {
                User user = topUsers.get(i);
                AbstractRanking<?> ranking = user.getUserRanking(rankingType);

                messageLines.add(StringUtils.color(
                        String.format("&a%d. %s (%d)", i + 1, user.getName(), (Integer) ranking.getRanking())
                ));
            }
        }
        return messageLines;
    }

}
