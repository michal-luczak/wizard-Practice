package me.taison.wizardpractice.game.matchmakingsystem.queue;

import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;

import java.util.concurrent.CopyOnWriteArrayList;

public class QueueToDuel {

    private final GameMapType gameMapType;
    private final CopyOnWriteArrayList<Team> teamsInQueue;

    public QueueToDuel(GameMapType gameMapType) {
        this.gameMapType = gameMapType;

        this.teamsInQueue = new CopyOnWriteArrayList<>();
    }

    public GameMapType getGameMapType() {
        return gameMapType;
    }

    public CopyOnWriteArrayList<Team> getTeamsInQueue() {
        return teamsInQueue;
    }

    public void addTeamToQueue(Team team) {
        teamsInQueue.add(team);
    }

    public void removeTeamFromQueue(Team team) {
        teamsInQueue.remove(team);
    }
}
