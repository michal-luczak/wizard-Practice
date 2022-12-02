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

    public DuelManager(CopyOnWriteArraySet<Arena> arenas) {
        this.arenas = arenas;
        this.runningDuels = new CopyOnWriteArraySet<>();
        this.waitingDuels = new ConcurrentLinkedDeque<>();
    }

    public int getRunningDuels(GameMapType gameMapType){
        return (int) this.runningDuels.stream().filter(duel -> duel.getGameMapType() == gameMapType).count();
    }

    public Optional<Duel> getDuelByPlayer(Player player) {
        for (Duel duel : runningDuels) {
            if (duel.getPlayer1().equals(player) || duel.getPlayer2().equals(player))
                return Optional.of(duel);
        }
        return Optional.empty();
    }

    public void startDuel(GameMapType gameMapType, Player player1, Player player2) {
        Duel duel = new Duel(gameMapType, player1, player2);

        getFreeArena().ifPresentOrElse(arena -> {
            runningDuels.add(duel);

            duel.setArena(arena);
            duel.startDuel();

            arena.setOccupied(true);
        }, () -> waitingDuels.add(duel));
    }

    public void stopDuel(Duel duel) {
        duel.stopDuel();

        runningDuels.remove(duel);

        duel.getArena().setOccupied(false);

        if (!waitingDuels.isEmpty()) {
            waitingDuels.peek().startDuel();

            duel.getArena().setOccupied(true);

            runningDuels.add(duel);
            waitingDuels.remove(duel);
        }
    }

    public CopyOnWriteArraySet<Arena> getArenas() {
        return arenas;
    }

    private Optional<Arena> getFreeArena() {
        return arenas.stream().filter(Arena::isFree).findFirst();
    }
}
