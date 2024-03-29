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

    private UUID uniqueIdentifier;

    private String name;

    private Map<GameMapType, CustomInventorySettings> customInventorySettingsMap;

    private Map<RankingType, AbstractRanking<?>> rankings;

    private User lastDamager;
    private long lastDamage;

    private Team team;

    private DefaultTablist defaultTablist;

    public UserImpl(UUID uniqueIdentifier, String name, boolean firstJoin){
        this.uniqueIdentifier = uniqueIdentifier;
        this.name = name;

        this.customInventorySettingsMap = new HashMap<>();

        this.rankings = new HashMap<>();

        if(firstJoin){
            EnumSet.allOf(RankingType.class).forEach(rankingType -> {
                AbstractRanking<?> abstractRanking = rankingType.getFor(this);

                this.rankings.put(rankingType, abstractRanking);

                WizardPractice.getSingleton().getRankingFactory().addRanking(abstractRanking);

                WizardPractice.getSingleton().getRankingFactory().update(this, rankingType);
            });
        }
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
    public Map<RankingType, AbstractRanking<?>> getRankings() {
        return this.rankings;
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

    @Override
    public String getTableName() {
        return "users";
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return "uuid";
    }

    @Override
    public Object getPrimaryKey() {
        return this.uniqueIdentifier.toString();
    }

    @Override
    public void fromResultSet(ResultSet resultSet) throws SQLException {
        //test \/
        UserPointsRanking pointsRanking = new UserPointsRanking(this, RankingType.POINTS);
        UserKillsRanking killsRanking = new UserKillsRanking(this, RankingType.KILLS);
        UserDeathRanking deathRanking = new UserDeathRanking(this, RankingType.DEATH);

        pointsRanking.setPoints(resultSet.getInt("points"));
        killsRanking.setKills(resultSet.getInt("kills"));
        deathRanking.setDeaths(resultSet.getInt("deaths"));

        this.rankings.putIfAbsent(RankingType.POINTS, pointsRanking);
        this.rankings.putIfAbsent(RankingType.KILLS, killsRanking);
        this.rankings.putIfAbsent(RankingType.DEATH, deathRanking);

        WizardPractice.getSingleton().getRankingFactory().addRanking(pointsRanking);
        WizardPractice.getSingleton().getRankingFactory().addRanking(killsRanking);
        WizardPractice.getSingleton().getRankingFactory().addRanking(deathRanking);
    }

    @Override
    public String[] getColumnNames() {
        return new String[] {"uuid", "nickname", "points", "kills", "deaths"};
    }

    @Override
    public Object[] getColumnValues() {
        return new Object[] {
                this.uniqueIdentifier.toString(),
                this.name,
                this.getUserRanking(RankingType.POINTS).getRanking(),
                this.getUserRanking(RankingType.KILLS).getRanking(),
                this.getUserRanking(RankingType.DEATH).getRanking()
        };
    }
}
