package me.taison.wizardpractice.data.user;

import me.taison.wizardpractice.data.storage.database.persistable.PersistableLoadable;
import me.taison.wizardpractice.data.storage.database.persistable.PersistableSaveable;
import me.taison.wizardpractice.data.user.impl.ranking.AbstractRanking;
import me.taison.wizardpractice.data.user.impl.CustomInventorySettings;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;
import me.taison.wizardpractice.game.tablist.DefaultTablist;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface User extends PersistableSaveable, PersistableLoadable {

    UUID getUniqueIdentifier();

    String getName();

    Optional<CustomInventorySettings> getCustomInventorySettings(GameMapType forGameType);

    AbstractRanking<?> getUserRanking(RankingType rankingType);

    Map<RankingType, AbstractRanking<?>> getRankings();

    DefaultTablist getTablist();

    long getLastDamage();

    User getLastDamager();

    void setLastDamager(User damager);

    Team getTeam();

    Player getAsPlayer();

    void sendMessage(String message);

    void sendTitle(String title, String subtitle, int fadeIn, int fadeOut, int time);

    void sendActionBar(String message);

    void setTeam(Team team);
}
