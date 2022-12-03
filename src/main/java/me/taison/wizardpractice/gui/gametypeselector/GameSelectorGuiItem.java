package me.taison.wizardpractice.gui.gametypeselector;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.impl.PracticeUser;
import me.taison.wizardpractice.duelsystem.DuelManager;
import me.taison.wizardpractice.duelsystem.queue.QueueDispatcher;
import me.taison.wizardpractice.gui.GuiItem;
import me.taison.wizardpractice.gui.GuiMenu;
import me.taison.wizardpractice.gui.event.GuiItemClickEvent;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class GameSelectorGuiItem extends GuiItem {
    private final GameMapType gameMapType;

    private final GameSelectorGui correspondigGui;

    private final DuelManager duelManager;

    private final QueueDispatcher queueDispatcher;

    public GameSelectorGuiItem(GameMapType gameMapType, GameSelectorGui correspondigGui){
        super(gameMapType.getName(), gameMapType.getItemStack());

        this.gameMapType = gameMapType;

        this.correspondigGui = correspondigGui;

        this.queueDispatcher = WizardPractice.getSingleton().getQueueDispatcher();

        this.duelManager = WizardPractice.getSingleton().getDuelManager();
    }

    @Override
    public ItemStack getFinalIcon(Player viewer) {
        ItemStack icon = getIcon().clone();
        ItemMeta meta = icon.getItemMeta();

        List<String> description = gameMapType.getDescription();

        int queuePlayers = 0;
        if(this.queueDispatcher.getQueueByGameType(gameMapType).isPresent()){
            queuePlayers = this.queueDispatcher.getQueueByGameType(gameMapType).get().getPlayersInQueue().size();
        }

        StringUtils.findAndReplace(description, "%queuedPlayer", String.valueOf(queuePlayers));
        StringUtils.findAndReplace(description, "%currentPlaying", String.valueOf(duelManager.getRunningDuels(gameMapType)));

        meta.setLore(description);

        icon.setItemMeta(meta);

        return icon;
    }

    @Override
    public void onItemClick(GuiItemClickEvent event) {
        Player player = event.getPlayer();

        switch (this.gameMapType) {
            case DIAMOND_GAME -> {
                player.sendMessage(StringUtils.color("&cTest1"));
                queueDispatcher.addPlayerToQueue(player, gameMapType);
                event.setWillClose(true);
            }
            case NORMAL_GAME -> {
                player.sendMessage(StringUtils.color("&cTest2"));
                queueDispatcher.addPlayerToQueue(player, gameMapType);
                event.setWillClose(true);
            }
            case SPEED_GAME -> {
                player.sendMessage(StringUtils.color("&cTest3"));
                queueDispatcher.addPlayerToQueue(player, gameMapType);
                event.setWillClose(true);
            }
        }
    }
}
