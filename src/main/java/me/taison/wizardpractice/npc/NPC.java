package me.taison.wizardpractice.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class NPC {

    private final int id;
    private final String name;
    private final String texture;
    private final String signature;
    private final Location location;
    private final GameMapType gameMapType;

    private EntityPlayer entityNPC;

    private NPC(final NpcBuilder builder) {

        this.name = builder.name;
        this.texture = builder.texture;
        this.signature = builder.signature;
        this.location = builder.location;
        this.gameMapType = builder.gameMapType;

        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getServer().getWorld("world")).getHandle();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), StringUtils.color(name));
        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));

        EntityPlayer entityNPC = new EntityPlayer(server, world, gameProfile, null);
        this.id = entityNPC.getBukkitEntity().getEntityId();

        entityNPC.forceSetPositionRotation(location.getBlockX(), location.getBlockY(), location.getBlockZ(), location.getYaw(), location.getPitch());
        entityNPC.ai().a(DataWatcherRegistry.a.a(16), (byte)127);
    }

    public void spawnNPC(Player playerToSendPacket) {

        PlayerConnection connection = ((CraftPlayer) playerToSendPacket).getHandle().b;

        connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, entityNPC));
        connection.a(new PacketPlayOutNamedEntitySpawn(entityNPC));

        new BukkitRunnable() {
            @Override
            public void run() {
                connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, entityNPC));
                connection.a(new PacketPlayOutEntityMetadata(entityNPC.getBukkitEntity().getEntityId(), entityNPC.ai(), true));
            }
        }.runTaskLater(WizardPractice.getPlugin(WizardPractice.class), 12L);
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

    public int getUniqueId() {
        return id;
    }

    public int getEntityId() {
        return id;
    }

    public EntityPlayer getEntityNPC() {
        return entityNPC;
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
