package me.taison.wizardpractice.duelsystem;

import me.taison.wizardpractice.duelsystem.arena.Arena;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.service.Service;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArraySet;

public class DuelManager {

    private final CopyOnWriteArraySet<Duel> runningDuels;
    private final ConcurrentLinkedDeque<Duel> waitingDuels;
    private final CopyOnWriteArraySet<Arena> arenas;

    public CopyOnWriteArraySet<Duel> getRunningDuels() {
        return runningDuels;
    }
    public ConcurrentLinkedDeque<Duel> getWaitingDuels() {
        return waitingDuels;
    }

    public DuelManager(CopyOnWriteArraySet<Arena> arenas) {
        this.arenas = arenas;
        this.runningDuels = new CopyOnWriteArraySet<>();
        this.waitingDuels = new ConcurrentLinkedDeque<>();
    }

    public int getRunningDuels(GameMapType gameMapType){
        return (int) this.runningDuels.stream().filter(duel -> duel.getGameMapType() == gameMapType).count();
    }

    public int getWaitingDuels(GameMapType gameMapType){
        return (int) this.waitingDuels.stream().filter(duel -> duel.getGameMapType() == gameMapType).count();
    }

    public Optional<Duel> getDuelByPlayer(Player player) {
        for (Duel duel : runningDuels) {
            if (duel.getPlayer1().equals(player) || duel.getPlayer2().equals(player))
                return Optional.of(duel);
        }
        return Optional.empty();
    }

    public void startDuel(GameMapType gameMapType, Player player1, Player player2) {
        Service.submit(() -> {
            Duel duel = new Duel(gameMapType, player1, player2);
            if (getFreeArena().isPresent()) {
                runningDuels.add(duel);
                duel.setArena(getFreeArena().get());
                duel.startDuel();
                getFreeArena().get().setOccupied(true);
            } else {
                waitingDuels.add(duel);
            }
        });
    }

    public void stopDuel(Duel duel) {
        Service.submit(() -> {
            duel.stopDuel();
            runningDuels.remove(duel);
            duel.getArena().setOccupied(false);
            if (!waitingDuels.isEmpty()) {
                waitingDuels.peek().startDuel();
                duel.getArena().setOccupied(true);
                runningDuels.add(duel);
                waitingDuels.remove(duel);
            }
        });
    }

    public CopyOnWriteArraySet<Arena> getArenas() {
        return arenas;
    }

    private Optional<Arena> getFreeArena() {
        return arenas.stream().filter(arena -> !arena.isOccupied()).findFirst();
    }
}
