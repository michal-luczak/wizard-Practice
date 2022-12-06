package me.taison.wizardpractice.game.matchmakingsystem.duel;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.ArenaState;
import me.taison.wizardpractice.game.matchmakingsystem.Duel;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DuelImpl implements Duel {

    private final List<Team> teams;

    private Map<Team, List<User>> aliveTeamMap;
    private final GameMapType gameMapType;
    private final DuelCounter duel;
    private Arena arena;

    private List<Location> placedBlocks;

    public DuelImpl(List<Team> teams, GameMapType gameMapType, Arena arena) {
        Validate.notNull(teams, "Teams cannot be null");
        Validate.notEmpty(teams, "Teams cannot be empty!");

        this.teams = new ArrayList<>(teams);

        this.aliveTeamMap = new ConcurrentHashMap<>(teams.size());

        this.gameMapType = gameMapType;

        this.duel = new DuelCounter(this, this.teams);

        this.arena = arena;

        this.placedBlocks = new ArrayList<>();
    }

    public void startDuel() {
        this.arena.setState(ArenaState.IN_GAME);

        this.teleportTeamsToArena();
        this.giveItems();

        this.duel.startCounting();
    }
    public void stopDuel() {
        this.arena.restartArena();

        this.teleportTeamsToSpawn();

        this.duel.cancel();

        this.placedBlocks.clear();
    }

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


    @Override
    public GameMapType getGameMapType() {
        return gameMapType;
    }

    @Override
    public DuelCounter getDuelCounter() {
        return duel;
    }

    @Override
    public Arena getArena() {
        return arena;
    }

    @Override
    public void setArena(Arena arena) {
        this.arena = arena;
    }

    @Override
    public List<Team> getTeams() {
        return teams;
    }

    @Override
    public void handleDeath(User killer, User victim) {
        if(this.getAliveTeamByUser(victim).isPresent()){
            Team aliveTeam = this.getAliveTeamByUser(victim).get();

            List<User> aliveTeamUsers = this.getAliveUsersByTeam(aliveTeam);

            if(aliveTeamUsers.size() == 1) {
                this.teams.forEach(team -> team.sendTitle("&aTeam &2" + killer.getTeam().getLeader().getName() + " &awygrywa", "&aGratulacje!", 0, 0, 100));
                this.stopDuel();
            } else if(aliveTeamUsers.size() >= 3){
                this.getAliveUsersByTeam(aliveTeam).remove(victim);

                killer.sendTitle("&a⚔ " + victim.getName(), "+ %punkty", 0, 0, 60); //TODO Ranking w przyszlosci

                this.teams.forEach(team -> team.sendMessage("&c" + killer.getName() + " zabija gracza " + victim.getName()));
            }
        }

    }

    private Optional<Team> getAliveTeamByUser(User user){
        for(Team team : this.aliveTeamMap.keySet()){
            if(team.getTeam().contains(user)){
                return Optional.of(team);
            }
        }
        return Optional.empty();
    }

    private List<User> getAliveUsersByTeam(Team team){
        return this.aliveTeamMap.get(team);
    }

    @Override
    public void handleBlockPlace(BlockPlaceEvent event, User user) {
        if(this.placedBlocks.contains(event.getBlock().getLocation())){
            this.placedBlocks.remove(event.getBlock().getLocation());
        }

        this.placedBlocks.add(event.getBlock().getLocation());

    }

    @Override
    public void handleBlockBreak(BlockBreakEvent event, User user) {
        if(!this.placedBlocks.contains(event.getBlock().getLocation())){
            event.getPlayer().sendMessage(StringUtils.color("&cMozesz niszczyc tylko bloki postawione przez innych graczy."));
            event.setCancelled(true);
        }
    }

}
