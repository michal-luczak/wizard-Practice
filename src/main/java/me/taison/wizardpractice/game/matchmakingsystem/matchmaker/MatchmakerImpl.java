package me.taison.wizardpractice.game.matchmakingsystem.matchmaker;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.ArenaFactory;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.matchmakingsystem.Duel;
import me.taison.wizardpractice.game.matchmakingsystem.Matchmaker;
import me.taison.wizardpractice.game.matchmakingsystem.duel.DuelImpl;
import me.taison.wizardpractice.game.matchmakingsystem.queue.QueueToDuel;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.random.RandomUtils;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MatchmakerImpl implements Matchmaker {

    private final ConcurrentLinkedQueue<Duel> runningDuel;

    private final CopyOnWriteArrayList<QueueToDuel> queuesToDuels;

    private final ArenaFactory arenaFactory;

    public MatchmakerImpl(ArenaFactory arenaFactory) {
        this.runningDuel = new ConcurrentLinkedQueue<>();
        this.queuesToDuels = new CopyOnWriteArrayList<>();
        this.arenaFactory = arenaFactory;

        arenaFactory.getArenas().forEach(arena -> arena.setMatchmaker(this));
        registerQueues();
    }


    //                           > MATCHMAKER <                         \\
    // Jeżeli istnieje możliwy do wystartowania duel to zwraca tego duela \\
    private List<Duel> tryMakeMatch() {
        List<Duel> duels = new ArrayList<>();

        for (QueueToDuel queue : queuesToDuels) {
            List<Team> teams = queue.getTeamsInQueue();

            int requiredSlots = queue.getGameMapType().getSlots();

            List<List<Team>> teamCombinations = teams.stream()
                    .filter(team -> team.getTeam().size() <= requiredSlots)
                    .collect(Collectors.groupingBy(team -> team.getTeam().size()))
                    .values()
                    .stream()
                    .flatMap(teamsWithSameSize -> {
                        List<List<Team>> combinations = new ArrayList<>();
                        for (int i = 0; i < teamsWithSameSize.size() - requiredSlots + 1; i++) {
                            combinations.add(teamsWithSameSize.subList(i, i + requiredSlots));
                        }
                        return combinations.stream();
                    }).toList();

            for (List<Team> matchedTeams : teamCombinations) {
                Optional<Arena> selectedArena = arenaFactory.getAvailableArenas(requiredSlots).stream()
                        .findFirst();

                if (selectedArena.isEmpty()) {
                    break;
                }

                Duel duel = new DuelImpl(this, matchedTeams, queue.getGameMapType(), selectedArena.orElse(null));
                duels.add(duel);

                WizardPractice.getSingleton().getQueueActionBarUpdateTask().setLastFoundGame(queue.getGameMapType(), System.currentTimeMillis() + matchedTeams.get(0).getWaitingTime(queue.getGameMapType()));
            }
        }

        return duels;
    }


    //      ADD/REMOVE TEAM TO QUEUE OPERATIONS      \\
    @Override
    public void removeTeamFromQueue(Team team) {
        getQueueByTeam(team).ifPresentOrElse(queue -> {
            queue.removeTeamFromQueue(team);
        }, () -> team.sendMessage("&cBłąd kolejki: Zgłos to do administratora!"));

        beginDuelRequest();
    }

    @Override
    public void addTeamToQueue(Team team, GameMapType gameMapType) {
        getQueueByGameType(gameMapType).ifPresentOrElse(queueToDuel -> {
            queueToDuel.addTeamToQueue(team);

            team.setWaitingTime(gameMapType, System.currentTimeMillis());
        }, () -> team.sendMessage("&cBłąd kolejki: Zgłos to do administratora!"));

        beginDuelRequest();
    }


    //      FINISH DUEL      \\
    @Override
    public void finishDuel(Duel duel) {
        System.out.println(1);
        duel.stopDuel();
        runningDuel.removeIf(duel1 -> duel1.equals(duel));
        beginDuelRequest();
    }

    @Override
    public void playerLeft(Duel duel, User user) {
        duel.playerLeft(user);
    }

    @Override
    public void beginDuelRequest() {

        //Jeżeli zwróci Duela to wystartuj
        tryMakeMatch().forEach(duel -> {
            duel.startDuel();
            duel.getTeams().forEach(this::removeTeamFromQueue);

            runningDuel.add(duel);
        });
    }


    //      GETTERS      \\
    @Override
    public ConcurrentLinkedQueue<Duel> getRunningDuels() {
        return runningDuel;
    }

    @Override
    public CopyOnWriteArrayList<QueueToDuel> getQueuesToDuels() {
        return queuesToDuels;
    }
}