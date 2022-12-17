package me.taison.wizardpractice.addons.impl;

import me.taison.wizardpractice.addons.AddonController;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import me.taison.wizardpractice.utilities.items.ItemBuilder;
import me.taison.wizardpractice.utilities.items.ItemStackUtils;
import me.taison.wizardpractice.utilities.random.RandomUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MagicChestAddon implements AddonController {

    private List<MagicChest> randomItems;

    private final ItemStack EMPTY = new ItemStack(Material.DIRT, 1);

    public MagicChestAddon(){
        this.randomItems = new ArrayList<>();
    }

    private final ItemStack MAGIC_CHEST = new ItemBuilder(Material.CHEST, 1).setName(StringUtils.color("&cMagiczna Skrzynka")).toItemStack();

    @Override
    public void initialize(){
        MagicChest item1 = new MagicChest(new ItemStack(Material.APPLE,
                8), 9, "&7Wylosowales &c8 jablek &7Gratulacje!");

        this.randomItems.add(item1);
    }

    public void giveRandomItems(Player player){
        MagicChest wonItem = this.getRandomCaseItem();

        ItemStackUtils.addItemsToPlayer(player, Collections.singletonList(wonItem.getItem()), player.getLocation());

        player.sendMessage(StringUtils.color(wonItem.getMessage()));
    }

    public MagicChest getRandomCaseItem() {
        for (MagicChest randomItem : randomItems) {
            if (RandomUtils.getChance(randomItem.getProbability())) {
                return randomItem;
            }
        }
        return this.randomItems.get(RandomUtils.getNextInt(this.randomItems.size()));
    }

    public ItemStack getMagicChest() {
        return MAGIC_CHEST;
    }

    public List<MagicChest> getCaseItems() {
        return randomItems;
    }

    @Override
    public void deinitialize(){
        this.randomItems.clear();
    }

}