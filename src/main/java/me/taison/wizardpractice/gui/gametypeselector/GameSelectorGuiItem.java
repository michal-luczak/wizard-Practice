package me.taison.wizardpractice.gui.gametypeselector;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.UserFactory;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.game.matchmakingsystem.Matchmaker;
import me.taison.wizardpractice.gui.GuiItem;
import me.taison.wizardpractice.gui.event.GuiItemClickEvent;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GameSelectorGuiItem extends GuiItem {
    private final GameMapType gameMapType;

    private final GameSelectorGui correspondigGui;

    private final Matchmaker matchmaker;

    public GameSelectorGuiItem(GameMapType gameMapType, GameSelectorGui correspondigGui){
        super(gameMapType.getName(), gameMapType.getItemStack());

        this.gameMapType = gameMapType;

        this.correspondigGui = correspondigGui;

        this.matchmaker = WizardPractice.getSingleton().getMatchmaker();
    }

    @Override
    public ItemStack getFinalIcon(Player viewer) {
        ItemStack icon = getIcon().clone();
        ItemMeta meta = icon.getItemMeta();

        List<String> description = gameMapType.getDescription();

        int queuePlayers = 0;
        if(this.matchmaker.getQueueByGameType(gameMapType).isPresent()) {
            queuePlayers = this.matchmaker.getQueueByGameType(gameMapType).get().getTeamsInQueue().size();
            queuePlayers += this.matchmaker.getAmountOfWaitingDuels(gameMapType);
        }

        StringUtils.findAndReplace(description, "%queuedPlayer", String.valueOf(queuePlayers));
        StringUtils.findAndReplace(description, "%currentPlaying", String.valueOf(matchmaker.getAmountOfRunningDuels(gameMapType)));

        meta.setDisplayName(gameMapType.getName());
        meta.setLore(description);

        icon.setItemMeta(meta);

        return icon;
    }

    @Override
    public void onItemClick(GuiItemClickEvent event) {
        Player player = event.getPlayer();

        UserFactory userFactory = WizardPractice.getSingleton().getUserFactory();
        if (userFactory.getByUniqueId(event.getPlayer().getUniqueId()).isEmpty())
            return;

        User user = userFactory.getByUniqueId(event.getPlayer().getUniqueId()).get();

        switch (this.gameMapType) {
            case DIAMOND_GAME -> {
                player.sendMessage(StringUtils.color("&cTest1"));
                matchmaker.addPlayerToQueue(user, gameMapType);
                event.setWillClose(true);
            }
            case NORMAL_GAME -> {
                player.sendMessage(StringUtils.color("&cTest2"));
                matchmaker.addPlayerToQueue(user, gameMapType);
                event.setWillClose(true);
            }
            case SPEED_GAME -> {
                player.sendMessage(StringUtils.color("&cTest3"));
                matchmaker.addPlayerToQueue(user, gameMapType);
                event.setWillClose(true);
            }
            case SPEED_GAME_MUTLI_TEAM -> {
                player.sendMessage(StringUtils.color("&cTest4"));
                matchmaker.addPlayerToQueue(user, gameMapType);
                event.setWillClose(true);
            }
            case NORMAL_GAME_MUTLI_TEAM -> {
                player.sendMessage(StringUtils.color("&cTest5"));
                matchmaker.addPlayerToQueue(user, gameMapType);
                event.setWillClose(true);
            }

            case DIAMOND_GAME_MUTLI_TEAM -> {
                player.sendMessage(StringUtils.color("&cTest6"));
                matchmaker.addPlayerToQueue(user, gameMapType);
                event.setWillClose(true);
            }
        }
    }
}
