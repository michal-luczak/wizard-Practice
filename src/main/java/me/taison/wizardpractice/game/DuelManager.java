package me.taison.wizardpractice.game;

import me.taison.wizardpractice.game.arena.Arena;
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

    public int getRunningDuels(GameMapType gameMapType){
        return (int) this.runningDuels.stream().filter(duel -> duel.getGameMapType() == gameMapType).count();
    }

    public Optional<Duel> getDuelByPlayer(Player player) {
        for (Duel duel : this.runningDuels) {
            if (duel.getPlayer1().equals(player) || duel.getPlayer2().equals(player))
                return Optional.of(duel);
        }
        return Optional.empty();
    }

    public void startDuel(GameMapType gameMapType, Player player1, Player player2) {
        Duel duel = new Duel(gameMapType, player1, player2);

        this.getFreeArena().ifPresentOrElse(arena -> {
            this.runningDuels.add(duel);

            duel.setArena(arena);
            duel.startDuel();

            arena.setOccupied(true);
        }, () -> waitingDuels.add(duel));
    }

    public void stopDuel(Duel duel) {
        duel.stopDuel();

        this.runningDuels.remove(duel);

        duel.getArena().setOccupied(false);

        if (!waitingDuels.isEmpty()) {
            this.waitingDuels.peek().startDuel();

            duel.getArena().setOccupied(true);

            this.runningDuels.add(duel);
            this.waitingDuels.remove(duel);
        }
    }

    public CopyOnWriteArraySet<Arena> getArenas() {
        return arenas;
    }

    private Optional<Arena> getFreeArena() {
        return arenas.stream().filter(Arena::isFree).findFirst();
    }
}
