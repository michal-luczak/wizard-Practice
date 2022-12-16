package me.taison.wizardpractice.data.factory;

import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;
import org.bukkit.Location;

import java.util.List;
import java.util.Optional;

public interface HologramFactory {

    void addHologram(String name, Location location);

    void updateHologram(String hologramName);

    void updateHologram(RankingType rankingType);

    List<String> findAll();

    default Optional<Hologram> findHologram(String string){
        return Optional.ofNullable(DHAPI.getHologram(string));
    }
}
