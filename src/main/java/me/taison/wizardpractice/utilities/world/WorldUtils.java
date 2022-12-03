package me.taison.wizardpractice.utilities.world;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WorldUtils {

    public static List<Player> getNearby(Location location, double radius){
        return Objects.requireNonNull(location.getWorld()).getNearbyEntities(location, radius, radius, radius).stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .collect(Collectors.toList());
    }

}
