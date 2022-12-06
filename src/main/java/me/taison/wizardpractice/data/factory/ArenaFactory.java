package me.taison.wizardpractice.data.factory;

import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.ArenaState;

import java.util.List;
import java.util.stream.Collectors;

public interface ArenaFactory {

    List<Arena> getArenas();

    void register(Arena arena);

    void unregister(Arena arena);

    default List<Arena> getAvailableArenas(int teamsAmount){
        return this.getArenas().stream().filter(arena -> arena.getState() == ArenaState.FREE && arena.isEnoughSpace(teamsAmount)).collect(Collectors.toList());
        //TODO orElse -> dynamiczne tworzenie areny ???
    }

}
