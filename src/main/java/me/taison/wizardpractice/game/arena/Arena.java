package me.taison.wizardpractice.game.arena;

import org.bukkit.Location;

import java.util.List;

public interface Arena {

    ArenaState getState();

    void setState(ArenaState arenaState);

    List<Location> getSpawnLocations();

    default boolean isEnoughSpace(int teamsAmount){
        return this.getSpawnLocations().size() >= teamsAmount;
    }

}
