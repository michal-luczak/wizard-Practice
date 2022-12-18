package me.taison.wizardpractice.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPC {

    private final UUID uuid;
    private final String name;
    private final String texture;
    private final String signature;
    private final Location location;
    private final GameMapType gameMapType;

    private NPC(final NpcBuilder builder) {

        this.name = builder.name;
        this.texture = builder.texture;
        this.signature = builder.signature;
        this.location = builder.location;
        this.gameMapType = builder.gameMapType;
        this.uuid = UUID.randomUUID();
    }

    public void spawnNPC(Player playerToSendPacket) {

        EntityPlayer entityPlayer = ((CraftPlayer) playerToSendPacket).getHandle();
        GameProfile gameProfile = new GameProfile(uuid, name);
        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));

        EntityPlayer npc = new EntityPlayer(entityPlayer.c, entityPlayer.s.getMinecraftWorld(), gameProfile, null);

        npc.forceSetPositionRotation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0, 0);
        npc.ai().a(DataWatcherRegistry.a.a(16), (byte)127);

        PlayerConnection connection = ((CraftPlayer) playerToSendPacket).getHandle().b;

        connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc));
        connection.a(new PacketPlayOutNamedEntitySpawn(npc));
        connection.a(new PacketPlayOutEntityMetadata(npc.getBukkitEntity().getEntityId(), npc.ai(), true));

        //TODO dokończyć i sprawdzić czy w ogóle działa XD
    }

    public void despawnNPC() {
        //TODO despawnNPC
    }




    public String getName() {
        return name;
    }

    public String getTexture() {
        return texture;
    }

    public String getSignature() {
        return signature;
    }

    public Location getLocation() {
        return location;
    }

    public GameMapType getGameMapType() {
        return gameMapType;
    }

    public UUID getUniqueId() {
        return uuid;
    }




    public static class NpcBuilder {

        private String name;
        private Location location;
        private String texture;
        private String signature;
        private GameMapType gameMapType;

        public NpcBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public NpcBuilder setLocation(Location location) {
            this.location = location;
            return this;
        }

        public NpcBuilder setTexture(String texture) {
            this.texture = texture;
            return this;
        }

        public NpcBuilder setSignature(String signature) {
            this.signature = signature;
            return this;
        }

        public NpcBuilder setGameMapType(GameMapType gameMapType) {
            this.gameMapType = gameMapType;
            return this;
        }

        public NPC build() {
            return new NPC(this);
        }
    }


}
