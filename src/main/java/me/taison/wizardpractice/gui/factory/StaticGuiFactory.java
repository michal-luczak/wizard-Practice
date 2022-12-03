package me.taison.wizardpractice.gui.factory;

import me.taison.wizardpractice.gui.gametypeselector.GameSelectorGui;

public class StaticGuiFactory {

    private final GameSelectorGui gameSelectorGui;

    public StaticGuiFactory(){
        this.gameSelectorGui = new GameSelectorGui();
    }

    public GameSelectorGui getGameSelectorGui() {
        return gameSelectorGui;
    }
}
