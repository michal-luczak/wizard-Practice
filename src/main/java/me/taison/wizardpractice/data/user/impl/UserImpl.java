package me.taison.wizardpractice.data.user.impl;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.AbstractRanking;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;
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

    private User lastDamager;
    private long lastDamage;

    private Team team;

    private Map<GameMapType, CustomInventorySettings> customInventorySettingsMap;

    private Map<RankingType, AbstractRanking<?>> rankings;

    public UserImpl(UUID uniqueIdentifier, String name){
        this.uniqueIdentifier = uniqueIdentifier;
        this.name = name;

        this.customInventorySettingsMap = new HashMap<>();

        this.rankings = new HashMap<>();
    }

    public UserImpl(ResultSet resultSet) throws SQLException {
        this.uniqueIdentifier = UUID.fromString(resultSet.getString("uuid"));
        this.name = resultSet.getString("name");
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

        AbstractRanking<?> abstractRanking = forRankingType.getFor(this);
        this.rankings.put(forRankingType, abstractRanking);

        //WizardPractice.getSingleton().getRankingFactory().update(this, forRankingType); todo

        return abstractRanking;
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

}
