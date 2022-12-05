package me.taison.wizardpractice.game.arena;

import me.taison.wizardpractice.game.matchmakingsystem.Matchmaker;
import org.bukkit.Location;

import java.util.List;

public interface Arena {

    ArenaState getState();

    void setState(ArenaState arenaState);

    void setMatchmaker(Matchmaker matchmaker);

    List<Location> getSpawnLocations();

    default boolean isEnoughSpace(int teamsAmount){
        return this.getSpawnLocations().size() >= teamsAmount;
    }

    default void restartArena() {
        this.setState(ArenaState.RESTARTING);
    }

}
