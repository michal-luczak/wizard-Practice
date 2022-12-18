package me.taison.wizardpractice.data.user.impl;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.AbstractRanking;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserDeathRanking;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserKillsRanking;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserPointsRanking;
import me.taison.wizardpractice.game.tablist.DefaultTablist;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserImpl implements User {

    private final UUID uniqueIdentifier;

    private final String name;

    private final Map<GameMapType, CustomInventorySettings> customInventorySettingsMap;

    private final Map<RankingType, AbstractRanking<?>> rankings;

    private User lastDamager;
    private long lastDamage;

    private Team team;

    private DefaultTablist defaultTablist;

    public UserImpl(UUID uniqueIdentifier, String name){
        this.uniqueIdentifier = uniqueIdentifier;
        this.name = name;

        this.customInventorySettingsMap = new HashMap<>();

        this.rankings = new HashMap<>();

        EnumSet.allOf(RankingType.class).forEach(rankingType -> {
            AbstractRanking<?> abstractRanking = rankingType.getFor(this);

            this.rankings.put(rankingType, abstractRanking);

            WizardPractice.getSingleton().getRankingFactory().addRanking(abstractRanking);

            WizardPractice.getSingleton().getRankingFactory().update(this, rankingType);
        });
    }

    public UserImpl(ResultSet resultSet) throws SQLException {
        this.customInventorySettingsMap = new HashMap<>();

        this.rankings = new HashMap<>();

        this.uniqueIdentifier = UUID.fromString(resultSet.getString("uuid"));
        this.name = resultSet.getString("nickname");

        AbstractRanking<?> pointsRanking = new UserPointsRanking(this, RankingType.POINTS);
        AbstractRanking<?> killsRanking = new UserKillsRanking(this, RankingType.KILLS);
        AbstractRanking<?> deathRanking = new UserDeathRanking(this, RankingType.DEATH);

        ((UserPointsRanking) pointsRanking).setPoints(resultSet.getInt("points"));
        ((UserKillsRanking) killsRanking).setKills(resultSet.getInt("kills"));
        ((UserDeathRanking) deathRanking).setDeaths(resultSet.getInt("deaths"));

        this.rankings.put(RankingType.POINTS, pointsRanking);
        this.rankings.put(RankingType.KILLS, killsRanking);
        this.rankings.put(RankingType.DEATH, deathRanking);

        WizardPractice.getSingleton().getRankingFactory().addRanking(pointsRanking);
        WizardPractice.getSingleton().getRankingFactory().addRanking(killsRanking);
        WizardPractice.getSingleton().getRankingFactory().addRanking(deathRanking);
    }

    @Override
    public Optional<CustomInventorySettings> getCustomInventorySettings(GameMapType forGameType) {
        return Optional.ofNullable(this.customInventorySettingsMap.getOrDefault(forGameType, new CustomInventorySettings(this)));
    }

    @Override
    public AbstractRanking<?> getUserRanking(RankingType forRankingType) {
        if (this.rankings != null && this.rankings.get(forRankingType) != null) {
            return this.rankings.get(forRankingType);
        }
        return null;
    }

    @Override
    public UUID getUniqueIdentifier() {
        return this.uniqueIdentifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public long getLastDamage() {
        return this.lastDamage;
    }

    @Override
    public void setLastDamager(User damager) {
        this.lastDamager = damager;

        this.lastDamage = System.currentTimeMillis();
    }

    @Override
    public User getLastDamager() {
        return lastDamager;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public Player getAsPlayer() {
        return Bukkit.getPlayer(this.uniqueIdentifier);
    }

    @Override
    public void sendMessage(String message) {
        this.getAsPlayer().sendMessage(StringUtils.color(message));
    }

    @Override
    public void sendTitle(String title, String subtitle, int fadeIn, int fadeOut, int time) {
        this.getAsPlayer().sendTitle(StringUtils.color(title), StringUtils.color(subtitle), fadeIn, fadeOut, time);
    }

    @Override
    public void sendActionBar(String message){
        this.getAsPlayer().sendActionBar(Component.text(StringUtils.color(message)));
    }

    @Override
    public void setTeam(Team team) {
        if(team == null){
            Team newTeam = new TeamImpl(this);

            WizardPractice.getSingleton().getTeamFactory().register(newTeam);

            this.team = newTeam;
            return;
        }
        this.team = team;
    }

    @Override
    public DefaultTablist getTablist(){
       if(this.defaultTablist == null){
           this.defaultTablist = new DefaultTablist(this);
       }
       return this.defaultTablist;
    }
}
