package me.taison.wizardpractice.data.user;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface User {

    UUID getUniqueIdentifier();

    String getName();

    long getLastDamage();

    void setLastDamager(User damager);

    Team getTeam();

    Player getAsPlayer();

    void sendMessage(String message);

    void sendTitle(String title, String subtitle, int fadeIn, int fadeOut, int time);

    void sendActionBar(String message);

    void setTeam(Team team);
}
