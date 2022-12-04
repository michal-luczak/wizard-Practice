package me.taison.wizardpractice.game.arena;

public interface Arena {

    ArenaState getState();

    void setState(ArenaState arenaState);

    default boolean canPlay(int teamsAmount){
        return false;
    }

}
