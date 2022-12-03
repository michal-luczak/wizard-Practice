package me.taison.wizardpractice.data.user.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TeamImpl implements Team {

    private Cache<User, String> cachedInvitations;

    private List<User> teamPlayers;

    private User leader;

    public TeamImpl(User leader){
        this.leader = leader;

        this.cachedInvitations = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.MINUTES).build();

        this.teamPlayers = new ArrayList<>();
        this.teamPlayers.add(leader);
    }

    @Override
    public List<User> getTeam() {
        return this.teamPlayers;
    }

    @Override
    public Cache<User, String> getInvitations() {
        return this.cachedInvitations;
    }

    @Override
    public User getLeader() {
        return this.leader;
    }

    @Override
    public void invitePlayer(User user) {
        this.teamPlayers.forEach(teamUser -> teamUser.sendMessage("&7" + user.getName() + " &6&nzostal zaproszony do druzyny."));
        user.sendMessage("&7Zostales zaproszony do druzyny &6" + this.leader.getName() + "&7");
        user.sendMessage("&7/dolacz &6" + this.leader.getName() + " &7aby dolaczyc.");
        user.sendMessage("&7Zaproszenie wygasnie po trzech minutach.");
        this.cachedInvitations.put(user,"");
    }

    @Override
    public void kickPlayer(User user) {
        this.teamPlayers.forEach(teamUser -> teamUser.sendMessage("&7" + user.getName() + " &6&nzostal wyrzucony z druzyny."));
        user.setTeam(null);
        this.teamPlayers.remove(user);
    }

    @Override
    public void disband() {
        this.teamPlayers.forEach(teamUser -> teamUser.sendMessage("&7" + this.leader.getName() + " &6&nrozwiazal druzyne."));
        this.teamPlayers.forEach(user -> user.setTeam(null));
        this.leader = null;
        this.teamPlayers.clear();
    }

    @Override
    public void leave(User user) {
        user.setTeam(null);
        this.teamPlayers.remove(user);
        this.teamPlayers.forEach(teamUser -> teamUser.sendMessage("&7" + user.getName() + " &6&nopuscil druzyne."));
    }

    @Override
    public void join(User user) {
        user.setTeam(this);
        this.teamPlayers.add(user);
        this.teamPlayers.forEach(teamUser -> teamUser.sendMessage("&7" + user.getName() + " &6&ndolaczyl do druzyny."));
    }

}
