package me.taison.wizardpractice.gui.gametypeselector;

import me.taison.wizardpractice.utilities.chat.StringUtils;
import me.taison.wizardpractice.utilities.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.List;

public enum GameMapType {

    CRYSTAL_PVP_DIAMOND(9, "&aCrystal pvp diamond", Arrays.asList("&cRodzaj gry: Diamentowe sety z kryształami", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.END_CRYSTAL, 1).addEnchant(Enchantment.KNOCKBACK, 1).toItemStack(),
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_BOOTS, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack(),
                    new ItemBuilder(Material.DIAMOND_LEGGINGS, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack(),
                    new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack(),
                    new ItemBuilder(Material.DIAMOND_HELMET, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack()
            },
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 5).toItemStack(),
                    new ItemBuilder(Material.END_CRYSTAL, 16).toItemStack(),
                    new ItemBuilder(Material.OBSIDIAN, 32).toItemStack(),
                    new ItemBuilder(Material.GOLDEN_APPLE, 16).toItemStack()
            }, 2),

    POTION(11, "&aPotion", Arrays.asList("&cRodzaj gry: Diamentowe sety na potki.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.POTION, 1).toItemStack(),
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_BOOTS, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack(),
                    new ItemBuilder(Material.DIAMOND_LEGGINGS, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack(),
                    new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack(),
                    new ItemBuilder(Material.DIAMOND_HELMET, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack()
            },
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 3).toItemStack(),
                    getPotion(PotionType.STRENGTH, 3, false),
                    getPotion(PotionType.SPEED, 3, true),
                    getPotion(PotionType.INSTANT_HEAL, 29, false),
            }, 2),

    CRYSTAL_PVP_NETHERITE(13, "&aCrystal pvp netherite", Arrays.asList("&cRodzaj gry: Netheritowe sety z kryształami.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.END_CRYSTAL, 1).toItemStack(),
            new ItemStack[]{
                    new ItemBuilder(Material.NETHERITE_BOOTS, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack(),
                    new ItemBuilder(Material.NETHERITE_LEGGINGS, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack(),
                    new ItemBuilder(Material.NETHERITE_CHESTPLATE, 1)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .toItemStack(),
                    new ItemBuilder(Material.NETHERITE_HELMET, 1)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .toItemStack()
            },
            new ItemStack[]{
                    new ItemBuilder(Material.NETHERITE_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 5).toItemStack(),
                    new ItemBuilder(Material.END_CRYSTAL, 16).toItemStack(),
                    new ItemBuilder(Material.OBSIDIAN, 32).toItemStack(),
                    new ItemBuilder(Material.GOLDEN_APPLE, 16).toItemStack()
    }, 2),

    ELYTRA(15, "&aElytra", Arrays.asList("&cRodzaj gry: Diamentowe sety z elytrą.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.ELYTRA, 1).toItemStack(),
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_BOOTS, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack(),
                    new ItemBuilder(Material.DIAMOND_LEGGINGS, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack(),
                    new ItemBuilder(Material.ELYTRA, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack(),
                    new ItemBuilder(Material.DIAMOND_HELMET, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack()
            },
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 4).toItemStack(),
                    new ItemBuilder(Material.FIREWORK_ROCKET, 128).toItemStack()
            }, 2),

    NORMAL(17, "&aNormal", Arrays.asList("&cRodzaj gry: Żelazne sety.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.DIAMOND_SWORD, 1).toItemStack(),
            new ItemStack[]{
                    new ItemBuilder(Material.IRON_BOOTS, 1).toItemStack(),
                    new ItemBuilder(Material.IRON_LEGGINGS, 1).toItemStack(),
                    new ItemBuilder(Material.IRON_CHESTPLATE, 1).toItemStack(),
                    new ItemBuilder(Material.IRON_HELMET, 1).toItemStack()
            },
            new ItemStack[]{
                    new ItemBuilder(Material.IRON_SWORD, 1).toItemStack(),
                    new ItemBuilder(Material.GOLDEN_APPLE, 8).toItemStack()
            }, 2),










    CRYSTAL_PVP_DIAMOND_MULTI_TEAM(18, "&aCrystal pvp diamond XvXvX", Arrays.asList("&cRodzaj gry: Diamentowe sety z kryształami", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.END_CRYSTAL, 1).addEnchant(Enchantment.KNOCKBACK, 1).toItemStack(),
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_BOOTS, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack(),
                    new ItemBuilder(Material.DIAMOND_LEGGINGS, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack(),
                    new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack(),
                    new ItemBuilder(Material.DIAMOND_HELMET, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack()
            },
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 5).toItemStack(),
                    new ItemBuilder(Material.END_CRYSTAL, 16).toItemStack(),
                    new ItemBuilder(Material.OBSIDIAN, 32).toItemStack(),
                    new ItemBuilder(Material.GOLDEN_APPLE, 16).toItemStack()
            }, 3),

    POTION_MULTI_TEAM(20, "&aPotion XvXvX", Arrays.asList("&cRodzaj gry: Diamentowe sety na potki.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.POTION, 1).toItemStack(),
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_BOOTS, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack(),
                    new ItemBuilder(Material.DIAMOND_LEGGINGS, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack(),
                    new ItemBuilder(Material.DIAMOND_CHESTPLATE, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack(),
                    new ItemBuilder(Material.DIAMOND_HELMET, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).toItemStack()
            },
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 3).toItemStack(),
                    getPotion(PotionType.STRENGTH, 3, false),
                    getPotion(PotionType.SPEED, 3, true),
                    getPotion(PotionType.INSTANT_HEAL, 29, false),
            }, 3),

    CRYSTAL_PVP_NETHERITE_MULTI_TEAM(22, "&aCrystal pvp netherite XvXvX", Arrays.asList("&cRodzaj gry: Netheritowe sety z kryształami.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.END_CRYSTAL, 1).toItemStack(),
            new ItemStack[]{
                    new ItemBuilder(Material.NETHERITE_BOOTS, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack(),
                    new ItemBuilder(Material.NETHERITE_LEGGINGS, 1)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .toItemStack(),
                    new ItemBuilder(Material.NETHERITE_CHESTPLATE, 1)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .toItemStack(),
                    new ItemBuilder(Material.NETHERITE_HELMET, 1)
                            .addEnchant(Enchantment.DURABILITY, 3)
                            .addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
                            .toItemStack()
            },
            new ItemStack[]{
                    new ItemBuilder(Material.NETHERITE_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 5).toItemStack(),
                    new ItemBuilder(Material.END_CRYSTAL, 16).toItemStack(),
                    new ItemBuilder(Material.OBSIDIAN, 32).toItemStack(),
                    new ItemBuilder(Material.GOLDEN_APPLE, 16).toItemStack()
            }, 3),

    ELYTRA_MULTI_TEAM(24, "&aElytra XvXvX", Arrays.asList("&cRodzaj gry: Diamentowe sety z elytrą.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.ELYTRA, 1).toItemStack(),
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_BOOTS, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack(),
                    new ItemBuilder(Material.DIAMOND_LEGGINGS, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack(),
                    new ItemBuilder(Material.ELYTRA, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack(),
                    new ItemBuilder(Material.DIAMOND_HELMET, 1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack()
            },
            new ItemStack[]{
                    new ItemBuilder(Material.DIAMOND_SWORD, 1).addEnchant(Enchantment.DAMAGE_ALL, 4).toItemStack(),
                    new ItemBuilder(Material.FIREWORK_ROCKET, 128).toItemStack()
            }, 3),

    NORMAL_MULTI_TEAM(26, "&aNormal XvXvX", Arrays.asList("&cRodzaj gry: Żelazne sety.", " ", "&7Graczy w kolejce: %queuedPlayer", "&7Graczy grających: %currentPlaying"),
            new ItemBuilder(Material.DIAMOND_SWORD, 1).toItemStack(),
            new ItemStack[]{
                    new ItemBuilder(Material.IRON_BOOTS, 1).toItemStack(),
                    new ItemBuilder(Material.IRON_LEGGINGS, 1).toItemStack(),
                    new ItemBuilder(Material.IRON_CHESTPLATE, 1).toItemStack(),
                    new ItemBuilder(Material.IRON_HELMET, 1).toItemStack()
            },
            new ItemStack[]{
                    new ItemBuilder(Material.IRON_SWORD, 1).toItemStack(),
                    new ItemBuilder(Material.GOLDEN_APPLE, 8).toItemStack()
            }, 3);


    private final int slotId;

    private final String name;
    private final List<String> description;

    private final ItemStack itemStack;

    private final ItemStack[] armor;

    private final ItemStack[] items;

    private final int slots;

    GameMapType(int slotId, String name, List<String> description, ItemStack itemStack, ItemStack[] armor, ItemStack[] items, int slots){
        this.slotId = slotId;

        this.name = name;
        this.description = description;
        this.itemStack = itemStack;
        this.armor = armor;
        this.items = items;
        this.slots = slots;
    }

    private static ItemStack getPotion(PotionType potionType, int amount, boolean lvl) {
        ItemStack item = new ItemStack(Material.POTION, amount);
        PotionMeta meta = (PotionMeta) item.getItemMeta();

        meta.setBasePotionData(new PotionData(potionType, false, lvl));
        item.setItemMeta(meta);

        return item;
    }

    public int getSlotId() {
        return slotId;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getName() {
        return StringUtils.color(name);
    }

    public List<String> getDescription() {
        return StringUtils.color(description);
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public int getSlots() {
        return slots;
    }
}
