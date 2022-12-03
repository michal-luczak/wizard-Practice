package me.taison.wizardpractice.duelsystem.queue;

import me.taison.wizardpractice.duelsystem.DuelManager;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class QueueDispatcher {

    private final CopyOnWriteArraySet<QueueToDuel> queuesToDuels;
    private final DuelManager duelManager;

    public QueueDispatcher(DuelManager duelManager) {
        this.queuesToDuels = new CopyOnWriteArraySet<>(getQueuesToDuel());
        this.duelManager = duelManager;
    }

    public CopyOnWriteArraySet<QueueToDuel> getQueuesToDuels() {
        return queuesToDuels;
    }


    public Optional<QueueToDuel> getQueueByGameType(GameMapType gameMapType) {
        return queuesToDuels.stream().filter(queue -> queue.getGameMapType().equals(gameMapType)).findFirst();
    }

    public Optional<QueueToDuel> getPlayerQueue(Player player) {
        return queuesToDuels.stream().filter(queue -> queue.getPlayersInQueue().contains(player)).findFirst();
    }

    //returns false if something went wrong
    public boolean addPlayerToQueue(Player player, GameMapType gameMapType) {
        if (getPlayerQueue(player).isPresent())
            return false;
        if (getQueueByGameType(gameMapType).isEmpty())
            return false;

        getQueueByGameType(gameMapType).get().addPlayerToQueue(player);
        if (getQueueByGameType(gameMapType).get().getPlayersInQueue().size() >= 2) {

            Player player1 = getQueueByGameType(gameMapType).get().getPlayersInQueue().peek();
            getQueueByGameType(gameMapType).get().getPlayersInQueue().remove();
            Player player2 = getQueueByGameType(gameMapType).get().getPlayersInQueue().peek();
            getQueueByGameType(gameMapType).get().getPlayersInQueue().remove();

            duelManager.startDuel(gameMapType, player1, player2);

        }
        player.getInventory().remove(duelManager.getFeather());
        player.getInventory().setItem(8, duelManager.getBarrier());
        return true;
    }

    public boolean removePlayerFromQueue(Player player) {
        if (getPlayerQueue(player).isEmpty())
            return false;
        getPlayerQueue(player).get().removePlayerFromQueue(player);

        player.getInventory().setItem(4, duelManager.getFeather());
        player.getInventory().remove(duelManager.getBarrier());

        return true;
    }

    private Set<QueueToDuel> getQueuesToDuel() {
        Set<QueueToDuel> queuesToDuel = new HashSet<>();

        queuesToDuel.add(new QueueToDuel(GameMapType.NORMAL_GAME));
        queuesToDuel.add(new QueueToDuel(GameMapType.DIAMOND_GAME));
        queuesToDuel.add(new QueueToDuel(GameMapType.SPEED_GAME));

        return queuesToDuel;
    }
}
