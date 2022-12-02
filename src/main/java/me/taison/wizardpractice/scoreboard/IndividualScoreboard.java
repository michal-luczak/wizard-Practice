package me.taison.wizardpractice.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class IndividualScoreboard {

    private final ConcurrentHashMap<Player, Scoreboard> scoreboards;

    public IndividualScoreboard() {
        this.scoreboards = new ConcurrentHashMap<>();
    }


    public ConcurrentHashMap<Player, Scoreboard> getScoreboards() {
        return scoreboards;
    }


    public void registerPlayerScoreboard(Player player) {
        scoreboards.put(player, Bukkit.getServer().getScoreboardManager().getNewScoreboard());
    }

    public void unregisterPlayerScoreboard(Player player) {
        scoreboards.remove(player);
    }

    public Optional<Scoreboard> getScoreboard(Player player) {
        return Optional.of(scoreboards.get(player));
    }
}
