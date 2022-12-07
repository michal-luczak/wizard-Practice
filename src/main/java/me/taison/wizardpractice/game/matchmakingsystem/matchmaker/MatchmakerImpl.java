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

        List<Arena> arenasXvX = arenaFactory.getAvailableArenas(2);
        List<Arena> arenasXvXvX = arenaFactory.getAvailableArenas(3);

        System.out.println(arenasXvX.size());
        System.out.println(arenasXvXvX.size());


        List<Duel> duels = new ArrayList<>();

        for (QueueToDuel queue : queuesToDuels) {

            int slots = queue.getGameMapType().getSlots();

            List<Team> teams = queue.getTeamsInQueue();

            teams.sort(Comparator.comparing(team -> team.getTeam().size()));

            for (int i=0; i<teams.size()-1; i++) {

                if (teams.get(i).getTeam().size() != teams.get(i+1).getTeam().size()) {
                    break;
                }
                if (slots == 3) {
                    break;
                }
                if (arenasXvX.size() == 0) {
                    break;
                }

                List<Team> matchedTeams = new ArrayList<>(Arrays.stream(new Team[]{teams.get(i), teams.get(i+1)}).toList());


                int index = arenasXvX.size() - 1;
                System.out.println("index: " +index);
                int indexOfArena = RandomUtils.getRandInt(0, index);

                Duel duel = new DuelImpl(matchedTeams, queue.getGameMapType(), arenasXvX.get(indexOfArena));
                duels.add(duel);
                System.out.println(arenasXvX);
                arenasXvX.remove(indexOfArena);

                i++;
            }

            for (int i=0; i<teams.size()-2; i++) {

                if ((teams.get(i).getTeam().size() != teams.get(i+1).getTeam().size())
                        || (teams.get(i).getTeam().size() != teams.get(i+2).getTeam().size())) {
                    break;
                }
                if (slots != 3) {
                    break;
                }

                List<Team> matchedTeams = new ArrayList<>(Arrays.stream(new Team[]{teams.get(i), teams.get(i+1), teams.get(i+2)}).toList());

                if (arenasXvXvX.size() == 0) {
                    break;
                }

                int indexOfArena = RandomUtils.getRandInt(0, arenasXvXvX.size()-1);

                Duel duel = new DuelImpl(matchedTeams, queue.getGameMapType(), arenasXvXvX.get(indexOfArena));
                duels.add(duel);
                arenasXvXvX.remove(indexOfArena);

                i++;
            }
        }

        return duels;
    }



    //      ADD/REMOVE TEAM TO QUEUE OPERATIONS      \\
    @Override
    public void removeTeamFromQueue(Team team) {
        getQueueByTeam(team).ifPresentOrElse(queue ->
                        queue.removeTeamFromQueue(team),
                () -> team.sendMessage("&cBłąd kolejki: Zgłos to do administratora!"));
        beginDuelRequest();
    }

    @Override
    public void addTeamToQueue(Team team, GameMapType gameMapType) {
        getQueueByGameType(gameMapType).ifPresentOrElse(queueToDuel ->
                        queueToDuel.addTeamToQueue(team),
                () -> team.sendMessage("&cBłąd kolejki: Zgłos to do administratora!"));
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