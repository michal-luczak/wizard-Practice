package me.taison.wizardpractice.game.matchmakingsystem;

import me.taison.wizardpractice.data.factory.ArenaFactory;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.ArenaState;
import me.taison.wizardpractice.game.matchmakingsystem.duel.Duel;
import me.taison.wizardpractice.game.matchmakingsystem.queue.QueueToDuel;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.random.RandomUtils;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class Matchmakerimpl implements Matchmaker {

    private final ConcurrentLinkedQueue<Duel> runningDuel;

    private final CopyOnWriteArrayList<QueueToDuel> queuesToDuels;

    private final ArenaFactory arenaFactory;



    public Matchmakerimpl(ArenaFactory arenaFactory) {
        this.runningDuel = new ConcurrentLinkedQueue<>();
        this.queuesToDuels = new CopyOnWriteArrayList<>();
        this.arenaFactory = arenaFactory;

        arenaFactory.getArenas().forEach(arena -> arena.setMatchmaker(this));
        registerQueues();
    }



     //                           > MATCHMAKER <                         \\
    // Jeżeli istnieje możliwy do wystartowania duel to zwraca tego duela \\
    private List<Duel> tryMakeMatch() {

        List<Arena> arenasXvX = new ArrayList<>(arenaFactory.getArenas()
                .stream()
                .filter(arena -> arena.getState() == ArenaState.FREE)
                .toList());

        List<Arena> arenasXvXvX = new ArrayList<>(arenaFactory.getArenas()
                .stream()
                .filter(arena -> arena.getState() == ArenaState.FREE)
                .toList());




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

                List<Team> matchedTeams = new ArrayList<>(Arrays.stream(new Team[]{teams.get(i), teams.get(i+1)}).toList());

                if (arenasXvX.isEmpty()) {
                    break;
                }

                int indexOfArena = RandomUtils.getRandInt(0, arenasXvX.size()-1);

                duels.add(new Duel(matchedTeams, queue.getGameMapType(), arenasXvX.get(indexOfArena)));
                arenasXvX.remove(indexOfArena);

                i++;
            }

            for (int i=0; i<teams.size()-2; i++) {

                if ((teams.get(i).getTeam().size() != teams.get(i+1).getTeam().size())
                        || (teams.get(i).getTeam().size() != teams.get(i+2).getTeam().size())) {
                    break;
                }

                List<Team> matchedTeams = new ArrayList<>(Arrays.stream(new Team[]{teams.get(i), teams.get(i+1), teams.get(i+2)}).toList());

                if (arenasXvXvX.isEmpty()) {
                    break;
                }

                int indexOfArena = RandomUtils.getRandInt(0, arenasXvXvX.size());

                duels.add(new Duel(matchedTeams, queue.getGameMapType(), arenasXvXvX.get(indexOfArena)));
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
                () -> team.sendMessage("coś sie zjebało z usuwaniem z kolejki"));
        beginDuelRequest();
    }

    @Override
    public void addTeamToQueue(Team team, GameMapType gameMapType) {
        getQueueByGameType(gameMapType).ifPresentOrElse(queueToDuel ->
                queueToDuel.addTeamToQueue(team),
                () -> team.sendMessage("coś sie zjebało z dodawaniem do kolejki"));
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
