package me.taison.wizardpractice.game.arena;

public interface Arena {

    default boolean canPlay(int teamsAmount){
        return false;
    }

}
