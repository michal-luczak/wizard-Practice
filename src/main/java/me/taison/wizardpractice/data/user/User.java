package me.taison.wizardpractice.data.user;

import me.taison.wizardpractice.data.user.impl.CustomInventorySettings;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface User {

    UUID getUniqueIdentifier();

    String getName();

    Optional<CustomInventorySettings> getCustomInventorySettings(GameMapType forGameType);

    long getLastDamage();

    User getLastDamager();

    void setLastDamager(User damager);

    Team getTeam();

    Player getAsPlayer();

    void sendMessage(String message);

    void sendTitle(String title, String subtitle, int fadeIn, int fadeOut, int time);

    void sendActionBar(String message);

    void setTeam(Team team);
}
