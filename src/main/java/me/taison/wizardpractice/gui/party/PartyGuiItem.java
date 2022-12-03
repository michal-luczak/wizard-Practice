package me.taison.wizardpractice.gui.party;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.UserFactory;
import me.taison.wizardpractice.gui.GuiItem;
import me.taison.wizardpractice.gui.event.GuiItemClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class PartyGuiItem extends GuiItem {

    private final PartyItems partyItem;

    private final PartyGui correspondigGui;

    public PartyGuiItem(PartyItems partyItem, PartyGui correspondigGui) {
        super(partyItem.getName(), partyItem.getItemStack());

        this.partyItem = partyItem;

        this.correspondigGui = correspondigGui;

    }

    @Override
    public ItemStack getFinalIcon(Player viewer) {
        ItemStack icon = getIcon().clone();
        ItemMeta meta = icon.getItemMeta();

        meta.setDisplayName(partyItem.getName());
        meta.setLore(Collections.singletonList(partyItem.getDescription()));

        icon.setItemMeta(meta);

        return icon;
    }

    @Override
    public void onItemClick(GuiItemClickEvent event) {
        Player player = event.getPlayer();

        UserFactory userFactory = WizardPractice.getSingleton().getUserFactory();

        userFactory.getByUniqueId(player.getUniqueId()).ifPresent(user -> {
            switch(this.partyItem){
                case ADD_FRIEND -> {

                }
                case REMOVE_FRIEND -> {

                }
                case FRIEND_LIST -> {

                }
                case LEAVE -> {

                }
                case DISBAND_PARTY -> {

                }
            }
        });


    }
}
