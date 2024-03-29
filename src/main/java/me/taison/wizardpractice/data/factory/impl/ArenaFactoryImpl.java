package me.taison.wizardpractice.data.factory.impl;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.ArenaFactory;
import me.taison.wizardpractice.game.arena.Arena;

import java.util.ArrayList;
import java.util.List;

public class ArenaFactoryImpl implements ArenaFactory {

    private final WizardPractice wizardPractice;

    private final List<Arena> arenaList;

    public ArenaFactoryImpl(WizardPractice wizardPractice){
        this.arenaList = new ArrayList<>();

        this.wizardPractice = wizardPractice;
    }

    @Override
    public List<Arena> getArenas() {
        return this.arenaList;
    }

    @Override
    public void register(Arena arena) {
        this.arenaList.add(arena);
    }

    @Override
    public void unregister(Arena arena) {
        this.arenaList.remove(arena);
    }
}
