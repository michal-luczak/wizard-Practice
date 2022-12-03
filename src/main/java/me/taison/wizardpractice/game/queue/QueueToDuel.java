package me.taison.wizardpractice.game.queue;

import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueToDuel {

    private final GameMapType gameMapType;
    private final ConcurrentLinkedQueue<Player> playersInQueue;

    public QueueToDuel(GameMapType gameMapType) {
        this.gameMapType = gameMapType;

        this.playersInQueue = new ConcurrentLinkedQueue<>();
    }

    public GameMapType getGameMapType() {
        return gameMapType;
    }

    public ConcurrentLinkedQueue<Player> getPlayersInQueue() {
        return playersInQueue;
    }

    public void addPlayerToQueue(Player player) {
        playersInQueue.add(player);
    }

    public void removePlayerFromQueue(Player player) {
        playersInQueue.remove(player);
    }
}
