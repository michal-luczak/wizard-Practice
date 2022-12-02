package me.taison.wizardpractice.utilities.items;

import me.taison.wizardpractice.utilities.random.RandomUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class ItemStackUtils {

    public static boolean isPickaxe(ItemStack tool) {
        return Enchantment.DIG_SPEED.canEnchantItem(tool);
    }

    public static void recalculateDurability(Player player, ItemStack item) {
        if (item.getType().getMaxDurability() == 0) {
            return;
        }
        int enchantLevel = item.getEnchantmentLevel(Enchantment.DURABILITY);
        short d = item.getDurability();

        if (enchantLevel > 0) {
            if (100 / (enchantLevel + 1) > RandomUtils.getRandInt(0, 100)) {
                if (d == item.getType().getMaxDurability()) {
                    player.getInventory().clear(player.getInventory().getHeldItemSlot());
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                } else {
                    item.setDurability((short) (d + 1));
                }
            }
        } else if (d == item.getType().getMaxDurability()) {
            player.getInventory().clear(player.getInventory().getHeldItemSlot());
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
        } else {
            item.setDurability((short) (d + 1));
        }
    }

    public static void addItemsToPlayer(Player player, List<ItemStack> items, Location location) {
        PlayerInventory inv = player.getInventory();

        Map<Integer, ItemStack> notStored = inv.addItem(items.toArray(new ItemStack[0]));

        for (Map.Entry<Integer, ItemStack> en : notStored.entrySet()) {
            Objects.requireNonNull(location.getWorld()).dropItemNaturally(location, en.getValue());
        }

    }

    public static void removeItem(final Player p, final ItemStack i, int a, final boolean checkname, final boolean ignoreenchanted) {
        if (p == null || i == null || a == 0) {
            return;
        }
        final ItemStack[] contents = p.getInventory().getContents();
        for (int j = contents.length - 1; j >= 0; --j) {
            if (i.getType() != null && contents[j] != null) {
                if (i.getType() == contents[j].getType()) {
                    if (i.getDurability() == contents[j].getDurability()) {
                        if (i.getItemMeta() != null && i.getItemMeta().getDisplayName() != null) {
                            if (contents[j].getItemMeta() == null) {
                                continue;
                            }
                            if (contents[j].getItemMeta().getDisplayName() == null) {
                                continue;
                            }
                        }
                        if (!checkname || contents[j].getItemMeta() == null || contents[j].getItemMeta().getDisplayName() == null || i.getItemMeta() == null || i.getItemMeta().getDisplayName() == null || i.getItemMeta().getDisplayName().equals(contents[j].getItemMeta().getDisplayName())) {
                            if (!ignoreenchanted || i.getItemMeta() == null || i.getItemMeta().getEnchants().size() <= 0) {
                                if (a < contents[j].getAmount()) {
                                    contents[j].setAmount(contents[j].getAmount() - a);
                                    p.updateInventory();
                                    return;
                                }
                                a -= contents[j].getAmount();
                                p.getInventory().setItem(j, null);
                            }
                        }
                    }
                }
            }
        }
    }

    public static int getItemAmount(ItemStack item, ItemStack[] contents) {
        int amount = 0;

        for (ItemStack is : contents) {
            if(is == null) continue;
            if (item.getType() == is.getType() && item.getData().getData() == is.getData().getData()) {
                amount += is.getAmount();
            }
        }

        return amount;
    }

    public static int getItemAmount(ItemStack item, Inventory inv) {
        int amount = 0;

        for (ItemStack is : inv.getContents()) {
            if(is == null) continue;;
            if (item.getType() == is.getType() && item.getData().getData() == is.getData().getData()) {
                amount += is.getAmount();
            }
        }

        return amount;
    }

}