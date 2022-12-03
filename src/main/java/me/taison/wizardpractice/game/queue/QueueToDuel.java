package me.taison.wizardpractice.game.queue;

import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.impl.TeamImpl;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;

import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueToDuel {

    private final GameMapType gameMapType;
    private final ConcurrentLinkedQueue<Team> teamsInQueue;

    public QueueToDuel(GameMapType gameMapType) {
        this.gameMapType = gameMapType;

        this.teamsInQueue = new ConcurrentLinkedQueue<>();
    }

    public GameMapType getGameMapType() {
        return gameMapType;
    }

    public ConcurrentLinkedQueue<Team> getTeamsInQueue() {
        return teamsInQueue;
    }

    public void addTeamToQueue(Team team) {
        teamsInQueue.add(team);
    }

    public void removeTeamFromQueue(Team team) {
        teamsInQueue.remove(team);
    }
}
