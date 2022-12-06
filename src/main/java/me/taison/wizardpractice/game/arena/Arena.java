package me.taison.wizardpractice.game.arena;

import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.matchmakingsystem.Matchmaker;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

public interface Arena {

    //TODO komentarze.

    ArenaState getState();

    void setState(ArenaState arenaState);

    void setMatchmaker(Matchmaker matchmaker);

    List<Location> getSpawnLocations();

    /*
     * @return boolean indicating whether the arena has enough room to hold the teams
     */
    default boolean isEnoughSpace(int teamsAmount){
        return this.getSpawnLocations().size() >= teamsAmount;
    }

    /*
     * This method is called when arena needs to be restarted.
     */
    default void restartArena() {
        this.setState(ArenaState.RESTARTING);
    }

}
