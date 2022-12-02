package me.taison.wizardpractice.duelsystem;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import org.bukkit.entity.Player;

public class Duel {

    private final Player player1, player2;
    private final DuelCounter duelCounter;
    private boolean isDuring;
    private final GameMapType gameMapType;

    private final DuelKit duelKit;

    private final Arena arena;

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public GameMapType getGameMapType() {
        return gameMapType;
    }

    public boolean isDuring() {
        return isDuring;
    }

    public DuelCounter getDuelCounter() {
        return duelCounter;
    }

    public DuelKit getDuelKit() {
        return duelKit;
    }

    public void setDuring(boolean during) {
        isDuring = during;
    }

    public Arena getArena() {
        return arena;
    }

    public Duel(GameMapType gameMapType, DuelKit duelKit, Player player1, Player player2, Arena arena) {
        this.gameMapType = gameMapType;
        this.duelKit = duelKit;
        this.player1 = player1;
        this.player2 = player2;
        this.arena = arena;
        this.duelCounter = new DuelCounter(player1, player2);
    }

    public void startDuel(Arena arena) {
        isDuring = true;
        teleportPlayersToArena(arena);
        giveItems();
        duelCounter.runTaskTimer(WizardPractice.getPlugin(WizardPractice.class), 0L, 20L);
    }

    public void stopDuel() {
        isDuring = false;
    }

    private void teleportPlayersToArena(Arena arena) {
        arena.setOccupied(true);
        player1.teleport(arena.getLocation());
        player2.teleport(arena.getLocation());
    }

    private void giveItems() {
        player1.getInventory().clear();
        player1.getInventory().setContents(duelKit.getItems());
        player1.getInventory().setArmorContents(duelKit.getArmor());
        player2.getInventory().clear();
        player2.getInventory().setContents(duelKit.getItems());
        player2.getInventory().setArmorContents(duelKit.getArmor());
    }
}
