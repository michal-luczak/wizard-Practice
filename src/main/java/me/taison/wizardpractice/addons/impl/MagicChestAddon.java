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
        MagicChest item2 = new MagicChest(new ItemStack(Material.GOLD_INGOT,
                8), 10, "&7Wylosowales &c8 sztabek zlota &7Gratulacje!");

        MagicChest item3 = new MagicChest(new ItemBuilder(Material.DIAMOND_PICKAXE, 1).
                setName(StringUtils.color("&6Kilof 6/3/3")).
                addEnchant(Enchantment.DIG_SPEED, 6).
                addEnchant(Enchantment.DURABILITY, 3).
                addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3).toItemStack(),
                0.75, "&7Wylosowales &cKilof 6/3/3 &7Gratulacje!");

        MagicChest item4 = new MagicChest(new ItemBuilder(Material.DIAMOND_HELMET, 1).
                setName(StringUtils.color("&63/3")).
                addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).
                addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                5, "&7Wylosowales &cHelm 3/3 &7Gratulacje!");

        MagicChest item5 = new MagicChest(new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1).
                setName(StringUtils.color("&63/3")).
                addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).
                addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                5, "&7Wylosowales &cKlate 3/3 &7Gratulacje!");

        MagicChest item6 = new MagicChest(new ItemBuilder(Material.DIAMOND_LEGGINGS, 1).
                setName(StringUtils.color("&63/3")).
                addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).
                addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                5, "&7Wylosowales &cNogawice 3/3 &7Gratulacje!");

        MagicChest item7 = new MagicChest(new ItemBuilder(Material.DIAMOND_BOOTS, 1).
                setName(StringUtils.color("&63/3")).
                addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).
                addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                5, "&7Wylosowales &cButy 3/3 &7Gratulacje!");
        MagicChest item8 = new MagicChest(new ItemStack(Material.GOLD_BLOCK,
                8), 6, "&7Wylosowales &c8 blokow zlota &7Gratulacje!");

        MagicChest item9 = new MagicChest(new ItemStack(Material.GOLDEN_APPLE,
                1, (short) 1), 6, "&7Wylosowales &c1 koxa &7Gratulacje!");

        MagicChest item10 = new MagicChest(new ItemStack(Material.GOLDEN_APPLE,
                4), 8, "&7Wylosowales &c4 refile &7Gratulacje!");

        MagicChest item11 = new MagicChest(new ItemStack(Material.GOLDEN_APPLE,
                16, (short) 1), 2, "&7Wylosowales &c16 koxow &7Gratulacje!");

        MagicChest item12 = new MagicChest(new ItemStack(Material.GOLDEN_APPLE,
                32), 2, "&7Wylosowales &c32 refili &7Gratulacje!");

        MagicChest item13 = new MagicChest(new ItemStack(Material.ENDER_PEARL,
                1), 8, "&7Wylosowales &c1 perle &7Gratulacje!");

        MagicChest item14 = new MagicChest(new ItemStack(Material.ENDER_PEARL,
                4), 3, "&7Wylosowales &c4 perly &7Gratulacje!");

        MagicChest item15 = new MagicChest(new ItemBuilder(Material.DIAMOND_SWORD, 1)
                .addEnchant(Enchantment.DAMAGE_ALL, 4)
                .addEnchant(Enchantment.FIRE_ASPECT, 1)
                .addEnchant(Enchantment.DURABILITY, 3).toItemStack(),
                5, "&7Wylosowales &cmiecz 4/1/3 &7Gratulacje!");

        MagicChest item17 = new MagicChest(new ItemStack(Material.TNT,
                64), 3, "&7Wylosowales &c64 tnt &7Gratulacje!");

        MagicChest item18 = new MagicChest(new ItemStack(Material.ENDER_PEARL,
                16), 1, "&7Wylosowales &c16 perly &7Gratulacje!");

        MagicChest item19 = new MagicChest(new ItemStack(Material.DIAMOND_BLOCK,
                1), 9, "&7Wylosowales &c1 blok diamentu &7Gratulacje!");

        MagicChest item20 = new MagicChest(new ItemBuilder(Material.PAPER, 1).setName(StringUtils.color("&aVoucher VIP")).toItemStack(), 0.3, "&7Wylosowales &cVoucher na VIP &7Gratulacje!");

        MagicChest item21 = new MagicChest(new ItemBuilder(Material.PAPER, 1).setName(StringUtils.color("&aVoucher SVIP")).toItemStack(), 0.2, "&7Wylosowales &cVoucher na SVIP &7Gratulacje!");

        MagicChest item23 = new MagicChest(new ItemStack(Material.BEACON,
                1), 0.5, "&7Wylosowales &c1 Beacon &7Gratulacje!");

        this.randomItems.add(item1);
        this.randomItems.add(item2);
        this.randomItems.add(item3);
        this.randomItems.add(item4);
        this.randomItems.add(item5);
        this.randomItems.add(item6);
        this.randomItems.add(item7);
        this.randomItems.add(item8);
        this.randomItems.add(item9);
        this.randomItems.add(item10);
        this.randomItems.add(item11);
        this.randomItems.add(item12);
        this.randomItems.add(item13);
        this.randomItems.add(item14);
        this.randomItems.add(item15);
        this.randomItems.add(item17);
        this.randomItems.add(item18);
        this.randomItems.add(item19);
        this.randomItems.add(item20);
        this.randomItems.add(item21);
        this.randomItems.add(item23);
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