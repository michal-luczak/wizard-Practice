package me.taison.wizardpractice.game;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.impl.ArenaImpl;
import me.taison.wizardpractice.game.arena.ArenaState;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Duel {

    private final List<Team> teams;
    private final GameMapType gameMapType;

    private final DuelCounter duelCounter;

    private boolean isDuring = false;

    private Arena arena;

    public Duel(List<Team> teams, GameMapType gameMapType) {
        Validate.notNull(teams, "Teams cannot be null");

        this.teams = new ArrayList<>(teams);

        this.gameMapType = gameMapType;

        this.duelCounter = new DuelCounter(this, this.teams);
    }

    public void startDuel() {
        this.isDuring = true;

        this.arena.setState(ArenaState.IN_GAME);

        this.teleportPlayersToArena();
        this.giveItems();

        this.duelCounter.startCounting();
    }

    public void stopDuel() {
        isDuring = false;

        arena.setState(ArenaState.RESTARTING);
        //TODO restartowanie areny
        arena.setState(ArenaState.FREE);

        duelCounter.cancel();

        this.teleportPlayersToSpawn();
    }

    private void teleportPlayersToArena() {
        //team1.getTeam().forEach(user -> user.getAsPlayer().teleport(arena.getLocation()));
        //team2.getTeam().forEach(user -> user.getAsPlayer().teleport(arena.getLocation()));

        AtomicInteger i = new AtomicInteger(0);
        this.teams.forEach(team -> {
            team.getTeam().stream().map(User::getAsPlayer).forEach(player -> {
                player.sendMessage(StringUtils.color("&aArenka sie zaczyna kurwyyy."));

                player.teleport(arena.getSpawnLocations().get(i.get()));

                i.getAndIncrement();
            });
        });
    }

    private void teleportPlayersToSpawn() {
        this.teams.forEach(team -> {
            team.getTeam().stream().map(User::getAsPlayer).forEach(player -> {
                player.sendMessage(StringUtils.color("&aArenka sie konczy kurwyyy."));

                player.teleport(WizardPractice.getSingleton().getSpawnLocation());

                player.getInventory().clear();
            });
        });
    }

    private void giveItems() {
        this.teams.forEach(team -> {
            team.getTeam().forEach(user -> {
                Player teamPlayer = user.getAsPlayer();

                teamPlayer.getInventory().clear();
                teamPlayer.updateInventory();

                teamPlayer.getInventory().setContents(gameMapType.getItems());
                teamPlayer.getInventory().setArmorContents(gameMapType.getArmor());
            });
        });
    }

    public GameMapType getGameMapType() {
        return gameMapType;
    }

    public boolean isDuring() {
        return isDuring;
    }

    public DuelCounter getDuelCounter() {
        return duelCounter;
    }

    public void setDuring(boolean during) {
        isDuring = during;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public List<Team> getTeams() {
        return teams;
    }
}
