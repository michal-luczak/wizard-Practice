package me.taison.wizardpractice.duelsystem;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum Arena {
    PRZYKLADOWA_ARENA("PRZYKLADOWA_ARENA", new Location(Bukkit.getWorlds().get(0), 0, 90 ,0));

    private final String name;
    private boolean isOccupied;
    private final Location location;

    Arena(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Location getLocation() {
        return location;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

}
