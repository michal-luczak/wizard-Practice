package me.taison.wizardpractice.game;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.arena.impl.ArenaImpl;
import me.taison.wizardpractice.game.arena.ArenaState;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Duel {

    private final Map<Team, List<User>> teams;
    private final GameMapType gameMapType;

    private final DuelCounter duelCounter;

    private boolean isDuring = false;

    private ArenaImpl arena;

    public Duel(Team team1, Team team2, GameMapType gameMapType) {
        Validate.notNull(team1, "Team cannot be null");
        Validate.notNull(team2, "Team cannot be null");

        this.teams = new HashMap<>();

        this.teams.putIfAbsent(team1, team1.getTeam());
        this.teams.putIfAbsent(team1, team1.getTeam());

        this.gameMapType = gameMapType;

        this.duelCounter = new DuelCounter(this, team1, team2);
    }

    public void startDuel() {
        this.isDuring = true;

        this.arena.setArenaState(ArenaState.IN_GAME);

        this.teleportPlayersToArena();
        this.giveItems();

        this.duelCounter.startCounting();
    }

    public void stopDuel() {
        isDuring = false;

        arena.setArenaState(ArenaState.RESTARTING);
        //TODO restartowanie areny
        arena.setArenaState(ArenaState.FREE);

        duelCounter.cancel();

        this.teleportPlayersToSpawn();
    }

    private void teleportPlayersToArena() {
        //team1.getTeam().forEach(user -> user.getAsPlayer().teleport(arena.getLocation()));
        //team2.getTeam().forEach(user -> user.getAsPlayer().teleport(arena.getLocation()));

        AtomicInteger i = new AtomicInteger();
        this.teams.forEach((team, users) -> {
            users.stream().map(User::getAsPlayer).forEach(player -> {
                player.sendMessage(StringUtils.color("&aArenka sie zaczyna kurwyyy."));

                player.teleport(arena.getSpawnLocations().get(i.get()));

                i.getAndIncrement();
            });
        });
    }

    private void teleportPlayersToSpawn() {
        this.teams.forEach((team, users) -> {
            users.stream().map(User::getAsPlayer).forEach(player -> {
                player.sendMessage(StringUtils.color("&aArenka sie konczy kurwyyy."));

                player.teleport(WizardPractice.getSingleton().getSpawnLocation());

                player.getInventory().clear();
            });
        });
    }

    private void giveItems() {
        this.teams.forEach((team, users) -> {

        });
        team1.getTeam().forEach(user -> {
            Player teamPlayer = user.getAsPlayer();

            teamPlayer.getInventory().clear();
            teamPlayer.updateInventory();

            user.getAsPlayer().getInventory().setContents(gameMapType.getItems());
            user.getAsPlayer().getInventory().setArmorContents(gameMapType.getArmor());

        });

        team2.getTeam().forEach(user -> {
            Player teamPlayer = user.getAsPlayer();

            teamPlayer.getInventory().clear();
            teamPlayer.updateInventory();

            user.getAsPlayer().getInventory().setContents(gameMapType.getItems());
            user.getAsPlayer().getInventory().setArmorContents(gameMapType.getArmor());
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

    public ArenaImpl getArena() {
        return arena;
    }

    public void setArena(ArenaImpl arena) {
        this.arena = arena;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }
}
