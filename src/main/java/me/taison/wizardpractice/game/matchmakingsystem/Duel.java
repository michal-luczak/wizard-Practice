package me.taison.wizardpractice.game.matchmakingsystem;

import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.matchmakingsystem.duel.DuelCounter;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.List;

public interface Duel {

    void startDuel();

    void stopDuel();

    void playerLeft(User user);

    GameMapType getGameMapType();

    DuelCounter getDuelCounter();

    List<Team> getTeams();

    Arena getArena();

    /*
     * This method is called when a player dies.
     *
     */
    void handleDeath(User killer, User victim);

    /*
     * This method is called when player is interacting with arena blocks.
     * Example: player is placing a block.
     */
    void handleBlockPlace(BlockPlaceEvent event, User user);


    /*
     * This method is called when player is interacting with arena blocks.
     * Example: player is breaking a block.
     */
    void handleBlockBreak(BlockBreakEvent event, User user);

}
