package me.taison.wizardpractice.game;

import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.impl.TeamImpl;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.ArenaState;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import org.apache.commons.lang3.Validate;

public class Duel {

    private final Team team1;

    private final Team team2;

    private final GameMapType gameMapType;

    private final DuelCounter duelCounter;

    private boolean isDuring = false;

    private Arena arena;

    public Duel(Team team1, Team team2, GameMapType gameMapType) {
        this.team1 = team1;
        this.team2 = team2;
        Validate.notNull(team1, "Team cannot be null");
        Validate.notNull(team2, "Team cannot be null");

        this.gameMapType = gameMapType;

        this.duelCounter = new DuelCounter(this, team1, team2);
    }

    public void startDuel() {
        isDuring = true;

        arena.setArenaState(ArenaState.IN_GAME);

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
    }

    private void teleportPlayersToArena() {
        team1.getTeam().forEach(user -> user.getAsPlayer().teleport(arena.getLocation()));
        team2.getTeam().forEach(user -> user.getAsPlayer().teleport(arena.getLocation()));
    }

    private void teleportPlayersToSpawn() {
        //TODO teleportowanie graczy z powrotem na spawna
    }

    private void giveItems() {
        team1.getTeam().forEach(user -> {
            user.getAsPlayer().getInventory().clear();
            user.getAsPlayer().getInventory().setContents(gameMapType.getItems());
            user.getAsPlayer().getInventory().setArmorContents(gameMapType.getArmor());
        });
        team2.getTeam().forEach(user -> {
            user.getAsPlayer().getInventory().clear();
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

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }
}
