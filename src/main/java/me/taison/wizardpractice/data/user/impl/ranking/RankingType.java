package me.taison.wizardpractice.data.user.impl.ranking;

import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserDeathRanking;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserKillsRanking;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserPointsRanking;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum RankingType {

    DEFEATED_PLAYERS("&aPokonani gracze", new Location(Bukkit.getWorld("world"), -8.5, 129, -60.5), new ItemStack(Material.DIAMOND_SWORD)),
    POINTS("&aNajwiecej punktów", new Location(Bukkit.getWorld("world"), -2.5, 129, -63.5), new ItemStack(Material.WRITABLE_BOOK)),
    DEATHS("&aNajwiecej śmierci.", new Location(Bukkit.getWorld("world"), 3.5, 129, -60.5), new ItemStack(Material.POINTED_DRIPSTONE));

    private final String name;

    private final Location hologramLocation;

    private final ItemStack hologramItemStack;

    RankingType(String name, Location hologramLocation, ItemStack hologramItemStack) {
        this.name = name;

        this.hologramLocation = hologramLocation;
        this.hologramItemStack = hologramItemStack;
    }

    public ItemStack getHologramItemStack() {
        return hologramItemStack;
    }

    public String getReadableName() {
        return StringUtils.color(name);
    }

    public Location getHologramLocation() {
        return hologramLocation;
    }

    public AbstractRanking<?> getFor(User user){
        return switch (this) {
            case DEFEATED_PLAYERS -> new UserKillsRanking(user, this);
            case POINTS -> new UserPointsRanking(user, this);
            case DEATHS -> new UserDeathRanking(user, this);
        };
    }
    public boolean isMatchingType(AbstractRanking<?> ranking) {
        return switch (this) {
            case DEFEATED_PLAYERS -> ranking instanceof UserKillsRanking;
            case DEATHS -> ranking instanceof UserDeathRanking;
            case POINTS -> ranking instanceof UserPointsRanking;
        };
    }

}
