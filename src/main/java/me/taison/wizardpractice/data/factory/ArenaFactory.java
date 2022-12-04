package me.taison.wizardpractice.data.factory;

import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.ArenaState;

import java.util.List;
import java.util.Optional;

public interface ArenaFactory {

    List<Arena> getArenas();

    void register(Arena arena);

    void unregister(Arena arena);

    default Optional<Arena> getAvailableArena(){
        return this.getArenas().stream().filter(arena -> arena.getState() == ArenaState.FREE).findAny();
        //TODO orElse -> dynamiczne tworzenie areny ???
    }

}
