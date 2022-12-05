package me.taison.wizardpractice.game.matchmakingsystem;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.ArenaFactory;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.ArenaState;
import me.taison.wizardpractice.game.matchmakingsystem.duel.Duel;
import me.taison.wizardpractice.game.matchmakingsystem.queue.QueueToDuel;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.gui.gametypeselector.GameSelectorGuiItem;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Matchmaker implements MatchObserver{

    private final ConcurrentLinkedQueue<Duel> waitingDuel;
    private final ConcurrentLinkedQueue<Duel> runningDuel;

    private final ArenaFactory arenaFactory;

    private final CopyOnWriteArrayList<QueueToDuel> queuesToDuels;



    public Matchmaker(ArenaFactory arenaFactory) {
        this.waitingDuel = new ConcurrentLinkedQueue<>();
        this.runningDuel = new ConcurrentLinkedQueue<>();
        this.arenaFactory = arenaFactory;
        this.queuesToDuels = new CopyOnWriteArrayList<>();

        arenaFactory.getArenas().forEach(arena -> arena.setMatchmaker(this));
    }



    //      MATCHMAKER      \\
    public void makeMatch() {
        //TODO matchmaker
    }



    //      ADD/REMOVE QUEUE OPERATIONS      \\
    public void removeUserFromQueue(User user) {
        //TODO
    }

    public void addPlayerToQueue(User user, GameMapType gameMapType) {
        //TODO
    }



    //      START/STOP DUEL      \\
    public void beginDuelRequest(GameMapType gameMapType, List<Team> teams) {
        //TODO
    }

    public void finishDuelRequest(Duel duel) {
        //TODO
    }



    @Override
    public void update(Arena arena) {

        if (arena.getState() != ArenaState.FREE) {
            WizardPractice.getSingleton().getLogger().info("[ERROR] Arena is not FREE");
            return;
        }

        if (waitingDuel.isEmpty()) {
            return;
        }

        makeMatch();
    }



    //      PRIVATE METHODS      \\
    private void registerQueues() {
        EnumSet.allOf(GameMapType.class).forEach(gameMapType -> queuesToDuels.add(new QueueToDuel(gameMapType)));
    }



    //      CUSTOM GETTERS      \\
    public int getAmountOfRunningDuels(GameMapType gameMapType) {
        return (int) this.runningDuel.stream().filter(duelImpl -> duelImpl.getGameMapType() == gameMapType).count();
    }

    public int getAmountOfWaitingDuels(GameMapType gameMapType) {
        return (int) this.waitingDuel.stream().filter(duelImpl -> duelImpl.getGameMapType() == gameMapType).count();
    }

    public Optional<Duel> getDuelByUser(User user) {
        for (Duel duel : this.runningDuel) {
            if (duel.getTeams().stream().anyMatch(team -> team.getTeam().contains(user))) {
                return Optional.of(duel);
            }
        }
        return Optional.empty();
    }

    public Optional<QueueToDuel> getQueueByGameType(GameMapType gameMapType) {
        return queuesToDuels.stream().filter(queue -> queue.getGameMapType().equals(gameMapType)).findFirst();
    }

    public Optional<QueueToDuel> getQueueByUser(User user) {
        return queuesToDuels.stream().filter(queue ->
                queue.getTeamsInQueue().stream().anyMatch(team -> team.getTeam().contains(user))).findFirst();
    }



    //      GETTERS      \\
    public ConcurrentLinkedQueue<Duel> getWaitingDuels() {
        return waitingDuel;
    }

    public ConcurrentLinkedQueue<Duel> getRunningDuels() {
        return runningDuel;
    }

    public ArenaFactory getArenaFactory() {
        return arenaFactory;
    }

    public CopyOnWriteArrayList<QueueToDuel> getQueuesToDuels() {
        return queuesToDuels;
    }
}
