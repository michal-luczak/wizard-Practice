package me.taison.wizardpractice.game;

import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.TeamImpl;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.ArenaState;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import me.taison.wizardpractice.utilities.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArraySet;

public class DuelManager {

    private final CopyOnWriteArraySet<Duel> runningDuels;
    private final ConcurrentLinkedDeque<Duel> waitingDuels;
    private final CopyOnWriteArraySet<Arena> arenas;

    private final ItemStack feather = new ItemBuilder(Material.FEATHER).addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addEnchant(Enchantment.ARROW_DAMAGE, 1).setName(StringUtils.color("&5&lWybór duela")).
            addLoreLine(StringUtils.color("&dKliknij aby zagrać!")).toItemStack();
    private final ItemStack barrier = new ItemBuilder(Material.BARRIER).addItemFlag(ItemFlag.HIDE_ENCHANTS)
            .addEnchant(Enchantment.ARROW_DAMAGE, 1).setName(StringUtils.color("&4&lAnulowanie duela")).
            addLoreLine(StringUtils.color("&cKliknij aby anulować!")).toItemStack();

    public DuelManager(CopyOnWriteArraySet<Arena> arenas) {
        this.arenas = arenas;
        this.runningDuels = new CopyOnWriteArraySet<>();
        this.waitingDuels = new ConcurrentLinkedDeque<>();
    }


    public ItemStack getFeather() {
        return feather;
    }
    public ItemStack getBarrier() {
        return barrier;
    }

    public int getRunningDuels(GameMapType gameMapType) {
        return (int) this.runningDuels.stream().filter(duel -> duel.getGameMapType() == gameMapType).count();
    }

    public Optional<Duel> getDuelByUser(User user) {
        for (Duel duel : this.runningDuels) {
            if (duel.getTeam1().getTeam().contains(user) || duel.getTeam2().getTeam().contains(user)) {
                return Optional.of(duel);
            }
        }
        return Optional.empty();
    }

    public void startDuel(GameMapType gameMapType, Team team1, Team team2) {
        Duel duel = new Duel(team1, team2, gameMapType);

        this.getFreeArena().ifPresentOrElse(arena -> {
            this.runningDuels.add(duel);

            duel.setArena(arena);
            duel.startDuel();

        }, () -> waitingDuels.add(duel));
    }

    public void stopDuel(Duel duel) {
        duel.stopDuel();

        this.runningDuels.remove(duel);

        if (!waitingDuels.isEmpty()) {
            this.waitingDuels.peek().startDuel();

            this.runningDuels.add(duel);
            this.waitingDuels.remove(duel);
        }
    }

    public CopyOnWriteArraySet<Arena> getArenas() {
        return arenas;
    }

    private Optional<Arena> getFreeArena() {
        return arenas.stream().filter(arena -> arena.getArenaState() == ArenaState.FREE).findFirst();
    }
}
