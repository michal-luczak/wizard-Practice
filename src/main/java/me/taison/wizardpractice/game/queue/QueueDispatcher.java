package me.taison.wizardpractice.game.queue;

import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.DuelManager;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;

import java.util.*;
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

    public Optional<QueueToDuel> getUserQueue(User user) {
        return queuesToDuels.stream().filter(queue ->
                queue.getTeamsInQueue().stream().anyMatch(team -> team.getTeam().contains(user))).findFirst();
    }

    //returns false if something went wrong
    public boolean addPlayerToQueue(User user, GameMapType gameMapType) {
        if (getUserQueue(user).isPresent())
            return false;
        if (getQueueByGameType(gameMapType).isEmpty())
            return false;

        getQueueByGameType(gameMapType).get().addTeamToQueue(user.getTeam());

        user.getAsPlayer().getInventory().remove(duelManager.getFeather());
        user.getAsPlayer().getInventory().setItem(8, duelManager.getBarrier());

        if (gameMapType.getSlots() == 3 && getQueueByGameType(gameMapType).get().getTeamsInQueue().size() >= 3) {

            //get team1
            Team team1 = getQueueByGameType(gameMapType).get().getTeamsInQueue().peek();
            getQueueByGameType(gameMapType).get().getTeamsInQueue().remove();

            //get team2
            Team team2 = getQueueByGameType(gameMapType).get().getTeamsInQueue().peek();
            getQueueByGameType(gameMapType).get().getTeamsInQueue().remove();

            //get team3
            Team team3 = getQueueByGameType(gameMapType).get().getTeamsInQueue().peek();
            getQueueByGameType(gameMapType).get().getTeamsInQueue().remove();

            duelManager.startDuel(gameMapType, new ArrayList<>(){{add(team1); add(team2); add(team3);}});
        } else if (gameMapType.getSlots() == 2 && getQueueByGameType(gameMapType).get().getTeamsInQueue().size() >= 2) {

            //get team1
            Team team1 = getQueueByGameType(gameMapType).get().getTeamsInQueue().peek();
            getQueueByGameType(gameMapType).get().getTeamsInQueue().remove();

            //get team2
            Team team2 = getQueueByGameType(gameMapType).get().getTeamsInQueue().peek();
            getQueueByGameType(gameMapType).get().getTeamsInQueue().remove();

            duelManager.startDuel(gameMapType, new ArrayList<>(){{add(team1); add(team2);}});
        }
        return true;
    }

    public boolean removeUserFromQueue(User user) {
        if (getUserQueue(user).isEmpty())
            return false;
        getUserQueue(user).get().removeTeamFromQueue(user.getTeam());

        user.getAsPlayer().getInventory().setItem(4, duelManager.getFeather());
        user.getAsPlayer().getInventory().remove(duelManager.getBarrier());

        return true;
    }

    private Set<QueueToDuel> getQueuesToDuel() {
        Set<QueueToDuel> queuesToDuel = new HashSet<>();

        queuesToDuel.add(new QueueToDuel(GameMapType.NORMAL_GAME));
        queuesToDuel.add(new QueueToDuel(GameMapType.DIAMOND_GAME));
        queuesToDuel.add(new QueueToDuel(GameMapType.SPEED_GAME));
        queuesToDuel.add(new QueueToDuel(GameMapType.NORMAL_GAME_MUTLI_TEAM));
        queuesToDuel.add(new QueueToDuel(GameMapType.DIAMOND_GAME_MUTLI_TEAM));
        queuesToDuel.add(new QueueToDuel(GameMapType.SPEED_GAME_MUTLI_TEAM));

        return queuesToDuel;
    }
}
