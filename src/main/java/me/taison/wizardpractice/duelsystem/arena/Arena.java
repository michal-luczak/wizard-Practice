package me.taison.wizardpractice.duelsystem.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public enum Arena {
    PRZYKLADOWA_ARENA("PRZYKLADOWA_ARENA", "world", new Location(Bukkit.getServer().getWorld("world"), 0, 90 ,0));

    private final String name;
    private final Location location;

    private final String world;
    private boolean isOccupied = false;
    //zamienic na ArenaState ^^

    private ArenaState arenaState;

    Arena(String name, String world, Location location) {
        this.name = name;
        this.location = location;
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public boolean isFree(){
        return !this.isOccupied;
    }

    public Location getLocation() {
        return location;
    }

    public void setArenaState(ArenaState arenaState) {
        this.arenaState = arenaState;
    }

    public ArenaState getArenaState() {
        return arenaState;
    }

    public World getWorld() {
        return Bukkit.getServer().getWorld(this.world);
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

}
