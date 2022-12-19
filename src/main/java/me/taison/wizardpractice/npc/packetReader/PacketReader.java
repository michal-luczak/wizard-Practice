package me.taison.wizardpractice.npc.packetReader;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.NPCFactory;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.npc.NPC;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import net.minecraft.network.protocol.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class PacketReader {

    private final Player player;
    private Channel channel;



    public PacketReader(Player player) {
        this.player = player;
    }



    public void inject() {
        CraftPlayer cPlayer = (CraftPlayer) this.player;

        channel = cPlayer.getHandle().b.b.m;
        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {
            @Override
            protected void decode(ChannelHandlerContext arg0, Packet<?> packet, List<Object> arg2) {
                arg2.add(packet);
                readPacket(packet);
            }
        });
    }

    public void uninject() {
        if (channel.pipeline().get("PacketInjector") != null) {
            channel.pipeline().remove("PacketInjector");
        }
    }



    public void readPacket(Packet<?> packet)  {

        if (!packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            return;
        }

        try {
            Object interaction = getValue(packet, "b");

            Method method = interaction.getClass().getDeclaredMethod("a");
            method.setAccessible(true);

            if (!method.invoke(interaction, null).toString().equalsIgnoreCase("INTERACT")) {
                return;
            }

            if (getValue(interaction, "a").toString().equals("OFF_HAND")) {
                return;
            }

            NPCFactory npcFactory = WizardPractice.getSingleton().getNpcFactory();

            npcFactory.getByEntityId((Integer) getValue(packet, "a")).ifPresent(npc -> {

                User user = WizardPractice.getSingleton().getUserFactory().getByUniqueId(player.getUniqueId()).orElseThrow();
                Team team = WizardPractice.getSingleton().getTeamFactory().getTeamByUser(user).orElseThrow();

                if (!team.getLeader().equals(user)) {
                    player.sendMessage(StringUtils.color("&cNie jesteś liderem drużyny!"));
                    return;
                }

                if (WizardPractice.getSingleton().getMatchmaker().getDuelByTeam(team).isPresent()) {
                    player.sendMessage(StringUtils.color("&cTwoja drużyna jest dalej w grze!"));
                    return;
                }

                WizardPractice.getSingleton().getMatchmaker().addTeamToQueue(team, npc.getGameMapType());
            });

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getValue(Object obj, String name) {

        try {
            Field field = obj.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception ignored) {}

        return null;
    }
}
