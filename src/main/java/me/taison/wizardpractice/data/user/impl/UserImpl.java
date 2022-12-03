package me.taison.wizardpractice.data.user.impl;

import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserImpl implements User {

    private final UUID uniqueIdentifier;

    private final String name;

    private User lastDamager;
    private long lastDamage;

    private Team team;

    public UserImpl(UUID uniqueIdentifier, String name){
        this.uniqueIdentifier = uniqueIdentifier;
        this.name = name;
    }

    public UserImpl(ResultSet resultSet) throws SQLException {
        this.uniqueIdentifier = UUID.fromString(resultSet.getString("uuid"));
        this.name = resultSet.getString("name");
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
    public void setTeam(Team team) {
        this.team = team;
    }

}
