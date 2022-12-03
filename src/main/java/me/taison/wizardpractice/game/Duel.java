package me.taison.wizardpractice.game;

import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

public class Duel {

    private final Player player1;
    private final Player player2;

    private final GameMapType gameMapType;

    private final DuelCounter duelCounter;

    private boolean isDuring = false;

    private Arena arena;

    public Duel(GameMapType gameMapType, Player player1, Player player2) {
        Validate.notNull(player1, "Player1 cannot be null");
        Validate.notNull(player2, "Player2 cannot be null");

        this.gameMapType = gameMapType;

        this.player1 = player1;
        this.player2 = player2;

        this.duelCounter = new DuelCounter(this, player1, player2);
    }

    public void startDuel() {
        isDuring = true;

        arena.setOccupied(true);

        this.teleportPlayers();
        this.giveItems();

        this.duelCounter.startCounting();
    }

    public void stopDuel() {
        isDuring = false;
    }

    private void teleportPlayers() {
        player1.teleport(arena.getLocation());
        player2.teleport(arena.getLocation());
    }

    private void giveItems() {
        player1.getInventory().clear();
        player1.getInventory().setContents(gameMapType.getItems());
        player1.getInventory().setArmorContents(gameMapType.getArmor());

        player2.getInventory().clear();
        player2.getInventory().setContents(gameMapType.getItems());
        player2.getInventory().setArmorContents(gameMapType.getArmor());
    }

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

    public void setDuring(boolean during) {
        isDuring = during;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }
}
