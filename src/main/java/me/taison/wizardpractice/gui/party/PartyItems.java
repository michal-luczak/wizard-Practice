package me.taison.wizardpractice.gui.party;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;
import java.util.UUID;

public enum PartyItems {

    ADD_FRIEND(10,"&aDodaj osobe do druzyny",
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzExMDFjNDk4YWFhYzkzZTc2YjhmMTZkMjljYmZhNDczZWQyNGQ5YzZjNzU1NjNjMjdlZWRkNDljOTYzZTk4YiJ9fX0=",
            "&cDodaje osobe do drużyny.",
            new ItemStack(Material.PLAYER_HEAD, 1)),

    REMOVE_FRIEND(13,"&aUsun osoby z druzyny", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmZkZjZkMzMyOTRkZWMwM2Y0NDlhOTdjMGZkY2E3MDE3NzNmMmUyMThlYzExNzcxZDZiNDhiYjcxMzIyYWIwMyJ9fX0=",
            "&cUsuwa osobę z drużyny.",
            new ItemStack(Material.PLAYER_HEAD, 1)),

    FRIEND_LIST(16,"&aLista osób w druzynie", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQ5YzBmNTcxYTViYzAwMTRmMzVjOGQzNzFmMzFmNmI1MWMxNjg2ZDcxMzU5NzU5YmFlODhjNjUzNzQ4MWEzYSJ9fX0=",
            "&cWyświetla listę osób w drużynie",
            new ItemStack(Material.PLAYER_HEAD, 1)),

    LEAVE(39,"&aOpuść drużyne", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjIyMTAwNTFmZjRmZjVhNzM5ZGY5NDUxYzc5YWYwZmQwYTNiMDU1NmMxMzg5M2Y3Yzk2YWY1OTk3ZDAxNjY1MiJ9fX0=",
            "&aOpuszcza drużynę.",
            new ItemStack(Material.PLAYER_HEAD, 1)),

    DISBAND_PARTY(41,"&aUsuń drużynę i ich członków", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTE3ZGM2NDU4MzZiOThkMTg5ZWY2YzA1MmNjMzRkNGQxMjdjOTRhMjEwM2EwNjNjOTExMzQ0MDM3OTE3MDc2OCJ9fX0=",
            "&cUsuwa drużynę oraz usuwa jej członków",
            new ItemStack(Material.PLAYER_HEAD, 1));


    private final String name;

    private final int slotId;

    private final String base64value;
    private final String description;

    private final ItemStack itemStack;

    PartyItems(int slotId, String name, String base64value, String description, ItemStack itemStack){
        this.slotId = slotId;

        this.name = name;
        this.description = description;
        this.itemStack = itemStack;

        this.base64value = base64value;

        SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
        skullMeta.setPlayerProfile(Bukkit.createProfile(UUID.randomUUID(), null));

        PlayerProfile playerProfile = skullMeta.getPlayerProfile();
        if(playerProfile != null) {
            playerProfile.setProperty(new ProfileProperty("textures", this.base64value));
            skullMeta.setPlayerProfile(playerProfile);

            itemStack.setItemMeta(skullMeta);
        } else {
            throw new IllegalStateException("player profile is null");
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlotId() {
        return slotId;
    }

    public String getName() {
        return StringUtils.color(name);
    }

    public String getDescription() {
        return StringUtils.color(description);
    }

}
