package me.taison.wizardpractice.game.matchmakingsystem.matchmaker;

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

        List<Arena> twoSlotsArenas = arenaFactory.getAvailableArenas(2);
        List<Arena> threeSlotsArenas = arenaFactory.getAvailableArenas(3);


        List<Duel> duels = new ArrayList<>();

        queuesToDuels.forEach(queue -> {

            List<Team> teams = queue.getTeamsInQueue().stream().sorted(Comparator.comparing(team -> team.getTeam().size())).toList();


            AtomicInteger i = new AtomicInteger(0);
            teams.stream()
                    .filter(team -> i.get() < teams.size()-1)
                    .filter(team -> teams.get(i.get()).getTeam().size() == teams.get(i.get() + 1).getTeam().size())
                    .filter(team -> !twoSlotsArenas.isEmpty())
                    .forEach(team -> {
                        Duel duel = new DuelImpl(
                                this,
                                new ArrayList<>(Arrays.stream(new Team[]{
                                        teams.get(i.get()),
                                        teams.get(i.get() + 1)
                                }).toList()),
                                queue.getGameMapType(),
                                twoSlotsArenas.get(RandomUtils.getRandInt(0, threeSlotsArenas.size() - 1)));

                        duels.add(duel);
                        twoSlotsArenas.remove(RandomUtils.getRandInt(0, threeSlotsArenas.size() - 1));

                        i.getAndIncrement();
                    });



            i.set(0);
            teams.stream()
                    .filter(team -> i.get() < teams.size()-2)
                    .filter(team -> teams.get(i.get()).getTeam().size() == teams.get(i.get() + 1).getTeam().size())
                    .filter(team -> teams.get(i.get()).getTeam().size() == teams.get(i.get() + 2).getTeam().size())
                    .filter(team -> !threeSlotsArenas.isEmpty())
                    .forEach(team -> {
                        Duel duel = new DuelImpl(
                                this,
                                new ArrayList<>(Arrays.stream(new Team[]{
                                        teams.get(i.get()),
                                        teams.get(i.get() + 1),
                                        teams.get(i.get() + 2)
                                }).toList()),
                                queue.getGameMapType(),
                                threeSlotsArenas.get(RandomUtils.getRandInt(0, threeSlotsArenas.size() - 1)));

                        duels.add(duel);
                        threeSlotsArenas.remove(RandomUtils.getRandInt(0, threeSlotsArenas.size() - 1));

                        i.getAndIncrement();
                        i.getAndIncrement();
                    });
        });


        return duels;
    }


    //      ADD/REMOVE TEAM TO QUEUE OPERATIONS      \\
    @Override
    public void removeTeamFromQueue(Team team) {
        getQueueByTeam(team).ifPresentOrElse(queue -> queue.removeTeamFromQueue(team), () -> team.sendMessage("&cBłąd kolejki: Zgłos to do administratora!"));
        beginDuelRequest();
    }

    @Override
    public void addTeamToQueue(Team team, GameMapType gameMapType) {
        getQueueByGameType(gameMapType).ifPresentOrElse(queueToDuel -> queueToDuel.addTeamToQueue(team), () -> team.sendMessage("&cBłąd kolejki: Zgłos to do administratora!"));
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