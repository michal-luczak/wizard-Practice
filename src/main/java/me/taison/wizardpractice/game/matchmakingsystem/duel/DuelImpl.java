package me.taison.wizardpractice.game.matchmakingsystem.duel;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.CustomInventorySettings;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserDeathRanking;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserKillsRanking;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.ArenaState;
import me.taison.wizardpractice.game.matchmakingsystem.Duel;
import me.taison.wizardpractice.data.user.impl.ranking.elo.EloAlgorithm;
import me.taison.wizardpractice.game.matchmakingsystem.Matchmaker;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import me.taison.wizardpractice.utilities.items.VariousItems;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class DuelImpl implements Duel {

    private final Arena arena;
    private final List<Team> teams;
    private final List<Location> placedBlocks;

    private final Map<Team, List<User>> aliveTeamMap;
    private final GameMapType gameMapType;
    private final DuelCounter duel;

    private final Matchmaker matchmaker;

    public DuelImpl(Matchmaker matchmaker, List<Team> teams, GameMapType gameMapType, Arena arena) {
        Validate.notNull(teams, "Teams cannot be null");
        Validate.notNull(matchmaker, "Matchmaker cannot be null");
        Validate.notNull(arena, "Arena cannot be null");

        Validate.notEmpty(teams, "Teams cannot be empty!");

        this.matchmaker = matchmaker;

        this.teams = new ArrayList<>(teams);

        this.aliveTeamMap = new ConcurrentHashMap<>(teams.size());

        this.teams.forEach(team -> {
            this.aliveTeamMap.put(team, team.getTeam());
        });

        this.gameMapType = gameMapType;

        this.duel = new DuelCounter(this, this.teams);

        this.arena = arena;

        this.placedBlocks = new ArrayList<>();
    }

    public void startDuel() {
        this.arena.setState(ArenaState.IN_GAME);

        this.teleportTeamsToArena();
        this.giveItemStacks();

        this.duel.startCounting();
    }
    public void stopDuel() {
        this.arena.restartArena(placedBlocks);

        this.teleportTeamsToSpawn();

        this.duel.cancel();
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
            team.teleport(WizardPractice.getSingleton().getSpawnLocation());

            team.clearInventory();

            team.getTeam().forEach(user -> {
                if (!user.getAsPlayer().isDead()) {
                    user.getAsPlayer().getInventory().setItem(4, VariousItems.FEATHER_ITEM);
                }
                user.getAsPlayer().setHealth(20);
            });
        });
    }

    private void giveItemStacks() {
        this.teams.forEach(team -> team.getTeam().forEach(user -> {
            Player teamPlayer = user.getAsPlayer();

            Optional<CustomInventorySettings> customInventorySettingsOpt = user.getCustomInventorySettings(this.gameMapType);
            if(customInventorySettingsOpt.isEmpty()) {
                teamPlayer.getInventory().clear();

                teamPlayer.getInventory().setContents(gameMapType.getItems());
                teamPlayer.getInventory().setArmorContents(gameMapType.getArmor());

                teamPlayer.updateInventory();

                throw new IllegalArgumentException("CustomInventorySettings is not present");
                //Info daje itemy i tak zeby w trakcie bledu gracz i tak mogl grac.
            } else {
                teamPlayer.getInventory().clear();

                if(customInventorySettingsOpt.get().hasSetCustomInventory()) {
                    customInventorySettingsOpt.get().giveCustomInventory();

                    teamPlayer.updateInventory();
                } else {
                    teamPlayer.getInventory().setContents(gameMapType.getItems());
                    teamPlayer.getInventory().setArmorContents(gameMapType.getArmor());

                    teamPlayer.updateInventory();
                }
            }
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
    public List<Team> getTeams() {
        return teams;
    }

    @Override
    public void playerLeft(User user){
        if(this.getAliveTeamByUser(user).isPresent()) {
            if (this.aliveTeamMap.size() == 2) {
                Team aliveTeam = this.getAliveTeamByUser(user).get();

                List<User> aliveTeamUsers = this.getAliveUsersByTeam(aliveTeam);

                if(aliveTeamUsers.size() > 1){
                    this.getAliveUsersByTeam(aliveTeam).remove(user);

                    this.teams.forEach(team -> team.sendMessage("&aGracz &2" + user.getName() + " wyszedl z areny."));
                } else if(aliveTeamUsers.size() == 1) {
                    if (user.getLastDamager() == null || (System.currentTimeMillis() - user.getLastDamage() > TimeUnit.SECONDS.toMillis(15))) {
                        this.teams.forEach(team -> team.sendMessage("&c" + user.getName() + "wyszedl z areny. Anulowanie gry."));

                        this.matchmaker.finishDuel(this);
                        return;
                    }

                    StringBuilder sb = new StringBuilder();
                    sb.append(StringUtils.color("&aTeam &2" + user.getLastDamager().getTeam().getLeader().getName() + " &awygrywa arene " + this.arena.getName()));
                    for(User teamUser : user.getLastDamager().getTeam().getTeam()){
                        sb.append(StringUtils.color("\n  ⤹ " + teamUser.getName())); //.append share uzytkownika (ile dostal ranku)
                    }

                    Bukkit.broadcast(Component.text(sb.toString()));
                    this.teams.forEach(team -> team.sendTitle("&aTeam &2" + user.getLastDamager().getTeam().getLeader().getName() + " &awygrywa", "&aGratulacje!", 0, 0, 100));

                    this.matchmaker.finishDuel(this);
                }
            }

            if(this.aliveTeamMap.size() > 2){
                Team aliveTeam = this.getAliveTeamByUser(user).get();

                List<User> aliveTeamUsers = this.getAliveUsersByTeam(aliveTeam);

                if(aliveTeamUsers.size() > 1){
                    this.getAliveUsersByTeam(aliveTeam).remove(user);

                    this.teams.forEach(team -> team.sendMessage("&aGracz &2" + user.getName() + " wyszedl z areny."));
                }

                if(aliveTeamUsers.size() == 1){
                    this.getAliveUsersByTeam(aliveTeam).remove(user);

                    this.removeAliveTeam(aliveTeam);

                    this.teams.forEach(team -> team.sendMessage("&aGracz &2" + user.getName() + " wyszedl z areny."));
                }

                if (user.getLastDamager() != null && (System.currentTimeMillis() - user.getLastDamage()) < TimeUnit.SECONDS.toMillis(15)) {
                    this.teams.forEach(team -> team.sendMessage("&c" + user.getName() + " zostal zabity przez " + user.getLastDamager().getName()));

                    int[] share = EloAlgorithm.updateRanking(user.getLastDamager(), user);

                    user.getLastDamager().sendTitle("&a⚔ " + user.getName(), "+" + EloAlgorithm.calculateRanking(user.getLastDamager(), user)[0] +" PKT", 0, 0, 60);
                }

            }
        }
    }

    @Override
    public void handleDeath(User killer, User victim) {
        victim.getAsPlayer().spigot().respawn();

        ((UserKillsRanking) killer.getUserRanking(RankingType.DEFEATED_PLAYERS)).addKill();
        ((UserDeathRanking) victim.getUserRanking(RankingType.DEATHS)).addDeath();

        WizardPractice.getSingleton().getRankingFactory().update(victim, RankingType.DEATHS);
        WizardPractice.getSingleton().getRankingFactory().update(killer, RankingType.DEFEATED_PLAYERS);

        if(this.getAliveTeamByUser(victim).isPresent()){
            Team aliveTeam = this.getAliveTeamByUser(victim).get();

            List<User> aliveTeamUsers = this.getAliveUsersByTeam(aliveTeam);

            if(aliveTeamUsers.size() > 1){

                this.getAliveUsersByTeam(aliveTeam).remove(victim);

                killer.sendTitle("&a⚔ " + victim.getName(), "+" + EloAlgorithm.calculateRanking(killer, victim)[0] + " PKT", 0, 0, 60);

                EloAlgorithm.updateRanking(killer, victim);

                this.teams.forEach(team -> team.sendMessage("&c" + killer.getName() + " zabija gracza " + victim.getName()));

            } else if(aliveTeamUsers.size() == 1) {

                int[] share = EloAlgorithm.updateRanking(killer, victim);

                this.teams.forEach(team -> {
                    team.sendMessage("&c" + killer.getName() + " zabija gracza " + victim.getName());
                    team.sendMessage("&c" + killer.getName() + " wyeliminował drużynę " + victim.getTeam().getLeader().getName());
                });

                this.removeAliveTeam(aliveTeam);
            }

            if(this.aliveTeamMap.size() == 1) {
                StringBuilder sb = new StringBuilder();
                sb.append(StringUtils.color("&aTeam &2" + killer.getTeam().getLeader().getName() + " &awygrywa arene " + this.arena.getName()));
                for(User user : killer.getTeam().getTeam()){
                    sb.append(StringUtils.color("\n  ⤹ " + user.getName())); //.append share uzytkownika (ile dostal ranku)
                }

                Bukkit.broadcast(Component.text(sb.toString()));

                this.teams.forEach(team -> team.sendTitle("&aTeam &2" + killer.getTeam().getLeader().getName() + " &awygrywa", "&aGratulacje!", 0, 0, 100));

                WizardPractice.getSingleton().getMatchmaker().finishDuel(this);
            }
        }
    }

    private void removeAliveTeam(Team team) {
        this.aliveTeamMap.remove(team);
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
        this.placedBlocks.remove(event.getBlock().getLocation());

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
