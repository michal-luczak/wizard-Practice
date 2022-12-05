package me.taison.wizardpractice.game.arena.impl;

import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.ArenaState;
import me.taison.wizardpractice.game.matchmakingsystem.Matchmaker;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ArenaImpl implements Arena {

    private final String name;
    private final List<Location> spawnLocations;
    private World world;
    private ArenaState arenaState;
    private Matchmaker matchmaker;



    public ArenaImpl(String name) {
        this.name = name;

        this.arenaState = ArenaState.FREE;

        this.spawnLocations = new ArrayList<>();
    }



    @Override
    public void restartArena() {
        //TODO restartowanie areny
        setArenaState(ArenaState.FREE);
    }



    //Getters
    @Override
    public ArenaState getState() {
        return this.arenaState;
    }

    public List<Location> getSpawnLocations() {
        return this.spawnLocations;
    }

    public String getName() {
        return this.name;
    }

    public World getWorld() {
        return this.world;
    }

    public Matchmaker getMatchmaker() {
        return this.matchmaker;
    }



    //Setters
    @Override
    public void setState(ArenaState arenaState) {
        this.arenaState = arenaState;
        if (arenaState == ArenaState.FREE) {
            matchmaker.update(this);
        }
    }

    @Override
    public void setMatchmaker(Matchmaker matchmaker){
        this.matchmaker = matchmaker;
    }



    //Builder
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

    public ArenaImpl setWorld(World world){
        this.world = world;
        return this;
    }

}
