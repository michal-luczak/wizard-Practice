package me.taison.wizardpractice.data.user.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TeamImpl implements Team {

    private Cache<User, String> cachedInvitations;

    private List<User> teamPlayers;

    private User leader;

    public TeamImpl(User leader){
        this.leader = leader;

        //TODO zamiana guava -> caffeine
        this.cachedInvitations = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.MINUTES)
                .removalListener((RemovalListener<User, String>) removal -> {
                    if (removal.getKey() != null) {
                        removal.getKey().sendMessage(StringUtils.color("&a&lDRUZYNY: &aTwoje zaproszenie wygasło lub dołączyłeś."));
                    }
                })
                .build();

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
    public void setLeader(User leader) {
        this.leader = leader;

        leader.sendMessage(StringUtils.color("&aStworzono ci druzyne oraz zostales jej liderem. Mozesz zarzadzac osobami w druzynie pod komenda /druzyna."));
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

        this.teamPlayers.clear();

        this.teamPlayers.add(this.leader);
        this.leader.setTeam(this);

        this.leader.sendMessage(StringUtils.color("&aDruzyna zostala zresetowana."));
    }

    @Override
    public void sendTitle(String title, String subtitle, int fadeIn, int fadeOut, int time){
        this.teamPlayers.forEach(user -> user.sendTitle(title, subtitle, fadeIn, fadeOut, time));
    }

    @Override
    public void sendActionBar(String message){
        this.teamPlayers.forEach(user -> user.sendActionBar(message));
    }

    @Override
    public void leave(User user) {
        user.setTeam(null);

        this.teamPlayers.remove(user);
        this.teamPlayers.forEach(teamUser -> teamUser.sendMessage("&7" + user.getName() + " &6&nopuscil druzyne."));
    }

    @Override
    public void sendMessage(String message){
        this.teamPlayers.forEach(user -> user.sendMessage(message));
    }

    @Override
    public void join(User user) {
        if(this.cachedInvitations.asMap().get(user) != null){
            this.cachedInvitations.asMap().remove(user);
        }
        user.getTeam().leave(user);

        user.setTeam(this);
        this.teamPlayers.add(user);
        this.teamPlayers.forEach(teamUser -> teamUser.sendMessage("&7" + user.getName() + " &6&ndolaczyl do druzyny."));
    }

    @Override
    public void teleport(Location location) {
        teamPlayers.forEach(user -> user.getAsPlayer().teleport(location));
    }

    @Override
    public void clearInventory() {
        teamPlayers.forEach(user -> user.getAsPlayer().getInventory().clear());
    }

}
