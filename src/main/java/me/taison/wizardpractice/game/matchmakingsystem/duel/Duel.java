package me.taison.wizardpractice.game.matchmakingsystem.duel;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.ArenaState;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Duel {

    private final List<Team> teams;
    private final GameMapType gameMapType;

    private final DuelCounter duel;

    private boolean isDuring = false;

    private Arena arena;



    public Duel(List<Team> teams, GameMapType gameMapType, Arena arena) {
        Validate.notNull(teams, "Teams cannot be null");
        Validate.notEmpty(teams, "Teams cannot be empty!");

        this.teams = new ArrayList<>(teams);

        this.gameMapType = gameMapType;

        this.duel = new DuelCounter(this, this.teams);

        this.arena = arena;
    }



    //      START/STOP DUEL      \\
    public void startDuel() {
        this.isDuring = true;

        this.arena.setState(ArenaState.IN_GAME);

        this.teleportTeamsToArena();
        this.giveItems();

        this.duel.startCounting();
    }
    public void stopDuel() {
        this.isDuring = false;

        this.arena.restartArena();

        this.teleportTeamsToSpawn();

        this.duel.cancel();
    }



    //      PRIVATE METHODS      \\
    private void teleportTeamsToArena() {

        AtomicInteger i = new AtomicInteger(0);

        this.teams.forEach(team -> {
            team.sendActionBar("&aRozpoczyna sie gra! Powodzenia!");
            team.sendMessage("&aGra za chwile sie rozpocznie. Powodzenia!");
            team.teleport(arena.getSpawnLocations().get(i.get()));

            i.getAndIncrement();
        });
    }
    private void teleportTeamsToSpawn() {

        this.teams.forEach(team -> {
            team.sendMessage(StringUtils.color("&aArena sie zakonczyla."));
            team.teleport(WizardPractice.getSingleton().getSpawnLocation());
            team.clearInventory();
        });
    }
    private void giveItems() {
        this.teams.forEach(team -> team.getTeam().forEach(user -> {
            Player teamPlayer = user.getAsPlayer();

            teamPlayer.getInventory().clear();
            teamPlayer.updateInventory();

            teamPlayer.getInventory().setContents(gameMapType.getItems());
            teamPlayer.getInventory().setArmorContents(gameMapType.getArmor());
        }));
    }


    //      GETTERS      \\
    public GameMapType getGameMapType() {
        return gameMapType;
    }
    public DuelCounter getDuelCounter() {
        return duel;
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
    public boolean isDuring() {
        return isDuring;
    }
}
