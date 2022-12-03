package me.taison.wizardpractice.game.arena.impl;

import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.ArenaState;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ArenaImpl implements Arena {
    //PRZYKLADOWA_ARENA("PRZYKLADOWA_ARENA", "world", new Location(Bukkit.getServer().getWorld("world"), 0, 90 ,0));

    private final String name;

    private List<Location> spawnLocations;

    private World world;

    private ArenaState arenaState;

    public ArenaImpl(String name) {
        this.name = name;

        this.arenaState = ArenaState.FREE;

        this.spawnLocations = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public ArenaImpl setWorld(World world){
        this.world = world;
        return this;
    }

    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public ArenaImpl addSpawnLocation(double x, double y, double z, float yaw, float pitch){
        this.spawnLocations.add(new Location(this.world, x, y, z, yaw, pitch));
        return this;
    }

    public ArenaImpl setArenaState(ArenaState arenaState) {
        this.arenaState = arenaState;
        return this;
    }

    public ArenaImpl build(){
        return this;
    }

    public ArenaState getArenaState() {
        return arenaState;
    }

    public World getWorld() {
        return this.world;
    }
}
