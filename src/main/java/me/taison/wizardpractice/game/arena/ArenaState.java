package me.taison.wizardpractice.game.arena;

import me.taison.wizardpractice.utilities.chat.StringUtils;

public enum ArenaState {

    FREE("&aWolna"), ERROR("&cBłąd areny."), IN_GAME("&cW trakcie gry."), RESTARTING("&cRestartowanie..."), DISABLED("&cWyłączona przez administratora.");

    private final String readableMeaning;

    ArenaState(String readableMeaning){
        this.readableMeaning = readableMeaning;
    }

    public String getReadableMeaning() {
        return StringUtils.color(readableMeaning);
    }
}
