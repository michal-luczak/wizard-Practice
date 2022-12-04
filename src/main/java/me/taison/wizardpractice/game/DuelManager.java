package me.taison.wizardpractice.game;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.impl.ArenaImpl;
import me.taison.wizardpractice.game.arena.ArenaState;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import me.taison.wizardpractice.utilities.items.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArraySet;

public class DuelManager {

    private final CopyOnWriteArraySet<Duel> runningDuels;
    private final ConcurrentLinkedDeque<Duel> waitingDuels;
    private final CopyOnWriteArraySet<Arena> arenas;

    private final WizardPractice wizardPractice;

    private final ItemStack feather = new ItemBuilder(Material.FEATHER).addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addEnchant(Enchantment.ARROW_DAMAGE, 1).setName(StringUtils.color("&5&lWybór duela")).
            addLoreLine(StringUtils.color("&dKliknij aby zagrać!")).toItemStack();
    private final ItemStack barrier = new ItemBuilder(Material.BARRIER).addItemFlag(ItemFlag.HIDE_ENCHANTS)
            .addEnchant(Enchantment.ARROW_DAMAGE, 1).setName(StringUtils.color("&4&lAnulowanie duela")).
            addLoreLine(StringUtils.color("&cKliknij aby anulować!")).toItemStack();

    public DuelManager(WizardPractice wizardPractice, CopyOnWriteArraySet<Arena> arenas) {
        this.arenas = arenas;
        this.runningDuels = new CopyOnWriteArraySet<>();
        this.waitingDuels = new ConcurrentLinkedDeque<>();

        this.wizardPractice = wizardPractice;
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
            if (duel.getTeams().stream().anyMatch(team -> team.getTeam().contains(user))) {
                return Optional.of(duel);
            }
        }
        return Optional.empty();
    }

    public void startDuel(GameMapType gameMapType, List<Team> teams) {
        Duel duel = new Duel(teams, gameMapType);

        wizardPractice.getArenaFactory().getAvailableArena(gameMapType.getSlots()).ifPresentOrElse(arena -> {
            this.runningDuels.add(duel);

            duel.setArena(arena);
            duel.startDuel();

        }, () -> {
            waitingDuels.add(duel);
            Bukkit.broadcast(Component.text("Zjebales cos nie ma arenki"));
        });
    }

    public void stopDuel(Duel duel) {
        duel.stopDuel();

        this.runningDuels.remove(duel);

        if (!waitingDuels.isEmpty() && wizardPractice.getArenaFactory().getAvailableArena(duel.getGameMapType().getSlots()).isPresent()) {
            this.waitingDuels.peek().setArena(wizardPractice.getArenaFactory().getAvailableArena(duel.getGameMapType().getSlots()).get());
            this.waitingDuels.peek().startDuel();

            this.runningDuels.add(duel);
            this.waitingDuels.remove(duel);
        }
    }

    public CopyOnWriteArraySet<Arena> getArenas() {
        return arenas;
    }
}
