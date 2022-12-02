package me.taison.wizardpractice.duelsystem;

import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.service.Service;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArraySet;

public class DuelManager {

    private final CopyOnWriteArraySet<Duel> duels;

    private final CopyOnWriteArraySet<Arena> arenas;

    public DuelManager(CopyOnWriteArraySet<Arena> arenas) {
        this.arenas = arenas;
        this.duels = new CopyOnWriteArraySet<>();
    }

    public CopyOnWriteArraySet<Duel> getDuels() {
        return duels;
    }

    public CopyOnWriteArraySet<Arena> getArenas() {
        return arenas;
    }

    public Optional<Duel> getDuelByPlayer(Player player) {
        for (Duel duel : duels) {
            if (duel.getPlayer1().equals(player) || duel.getPlayer2().equals(player))
                return Optional.of(duel);
        }
        return Optional.empty();
    }

    public void startDuel(GameMapType gameMapType, Player player1, Player player2) {
        Service.submit(() -> {
            if (getFreeArena().isPresent()) {
                Duel duel = new Duel(gameMapType, player1, player2, getFreeArena().get());
                duels.add(duel);
                duel.startDuel(getFreeArena().get());
                getFreeArena().get().setOccupied(true);
            } else {
                //TODO Czekanie na wolną arene
            }
        });
    }

    public void stopDuel(Duel duel) {
        Service.submit(() -> {
            duel.stopDuel();
            duels.remove(duel);
            duel.getArena().setOccupied(false);
        });
    }

    private Optional<Arena> getFreeArena() {
        return arenas.stream().filter(arena -> !arena.isOccupied()).findFirst();
    }
}
