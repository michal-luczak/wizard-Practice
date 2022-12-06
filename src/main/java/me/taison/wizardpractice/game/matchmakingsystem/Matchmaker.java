package me.taison.wizardpractice.game.matchmakingsystem;

import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.matchmakingsystem.duel.Duel;
import me.taison.wizardpractice.game.matchmakingsystem.queue.QueueToDuel;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;

import java.util.EnumSet;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Matchmaker {
    void removeTeamFromQueue(Team team);

    void addTeamToQueue(Team team, GameMapType gameMapType);

    //      FINISH DUEL      \\
    void finishDuel(Duel duel);


    void beginDuelRequest();

    default void registerQueues() {
        EnumSet.allOf(GameMapType.class).forEach(gameMapType -> getQueuesToDuels().add(new QueueToDuel(gameMapType)));
    }

    default int getAmountOfRunningDuels(GameMapType gameMapType) {
        return (int) this.getRunningDuels().stream().filter(duelImpl -> duelImpl.getGameMapType() == gameMapType).count();
    }


    default Optional<Duel> getDuelByUser(User user) {
        for (Duel duel : getRunningDuels()) {
            if (duel.getTeams().stream().anyMatch(team -> team.getTeam().contains(user))) {
                return Optional.of(duel);
            }
        }
        return Optional.empty();
    }

    default Optional<QueueToDuel> getQueueByGameType(GameMapType gameMapType) {
        return getQueuesToDuels().stream().filter(queue -> queue.getGameMapType().equals(gameMapType)).findFirst();
    }

    default Optional<QueueToDuel> getQueueByTeam(Team team) {
        return getQueuesToDuels().stream().filter(queue ->
                queue.getTeamsInQueue().stream().anyMatch(team1 -> team1.equals(team))).findFirst();
    }

    ConcurrentLinkedQueue<Duel> getRunningDuels();


    CopyOnWriteArrayList<QueueToDuel> getQueuesToDuels();
}
