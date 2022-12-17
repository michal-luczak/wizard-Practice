package me.taison.wizardpractice.game.tablist;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.world.level.EnumGamemode;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;

import java.util.*;

public class DefaultTablist {

    //Trzeba dorobic jakis manager tablisty nwm TablistFactory, trzeba tam header footer defaultowa tabliste zapisywac
    //Tutaj dopiero to przerabiac z racji ze to jest obiekt kazdego usera, to bez sensu wszystko sto razy powielać

    private final User user;

    private List<String> tabList;

    private final String header = "&3&lWIZARDMC.EU. \n &eZnajdujesz sie na PRACTICE \n &eTo sa testy tego trybu";
    private final String footer = "&aAktualnie trwaja testy trybu. \n &aJak znajdziesz blad stworz ticket na discordzie.";

    private final EnumGamemode DEFAULT_GAME_MODE = EnumGamemode.a;
    private final IChatBaseComponent EMPTY_COMPONENT = IChatBaseComponent.a("{\"translate\":\"\"}");

    private final GameProfile[] profileCache = new GameProfile[80];

    private boolean firstPacket = true;

    private final Map<RankingType, String> rankingNames;

    public DefaultTablist(User user){
        this.user = user;

        this.rankingNames = new HashMap<>();
        rankingNames.put(RankingType.POINTS, "POINTS");
        rankingNames.put(RankingType.DEATH, "DEATH");
        rankingNames.put(RankingType.KILLS, "KILLS");

        this.setupTablist();
    }

    private void setupTablist(){
        //TODO kiedys config - Przygotowania na 22 grudnia wiec trzeba szybciutko.

        tabList = Arrays.asList(
                "",
                "",
                "  &e&lTOPKA PUNKTÓW",
                "",
                "{TOP-POINTS-1}",
                "{TOP-POINTS-2}",
                "{TOP-POINTS-3}",
                "{TOP-POINTS-4}",
                "{TOP-POINTS-5}",
                "{TOP-POINTS-6}",
                "{TOP-POINTS-7}",
                "{TOP-POINTS-8}",
                "{TOP-POINTS-9}",
                "{TOP-POINTS-10}",
                "{TOP-POINTS-11}",
                "{TOP-POINTS-12}",
                "{TOP-POINTS-13}",
                "",
                "",
                "",
                "",
                "",
                "  &e&lTOPKA SMIERCI",
                "",
                "{TOP-DEATH-1}",
                "{TOP-DEATH-2}",
                "{TOP-DEATH-3}",
                "{TOP-DEATH-4}",
                "{TOP-DEATH-5}",
                "{TOP-DEATH-6}",
                "{TOP-DEATH-7}",
                "{TOP-DEATH-8}",
                "{TOP-DEATH-9}",
                "{TOP-DEATH-10}",
                "{TOP-DEATH-11}",
                "{TOP-DEATH-12}",
                "{TOP-DEATH-13}",
                "",
                "",
                "",
                "",
                "",
                "  &e&lINFORMACJE O TOBIE",
                "",
                "&7Zabójstwa: {kills}",
                "&7Smierci: {deaths}",
                "&7Twoj nick: {nick}",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "  &e&lTOPKA ZABOJSTW",
                "",
                "{TOP-KILLS-1}",
                "{TOP-KILLS-2}",
                "{TOP-KILLS-3}",
                "{TOP-KILLS-4}",
                "{TOP-KILLS-5}",
                "{TOP-KILLS-6}",
                "{TOP-KILLS-7}",
                "{TOP-KILLS-8}",
                "{TOP-KILLS-9}",
                "{TOP-KILLS-10}",
                "{TOP-KILLS-11}",
                "{TOP-KILLS-12}",
                "{TOP-KILLS-13}",
                "",
                "",
                ""
        );
    }

    public void send(){
        Player player = this.user.getAsPlayer();

        this.prepareCells();

        List<Packet<?>> packets = Lists.newArrayList();
        List<PacketPlayOutPlayerInfo.PlayerInfoData> addPlayerList = Lists.newArrayList();
        List<PacketPlayOutPlayerInfo.PlayerInfoData> updatePlayerList = Lists.newArrayList();

        try {
            for (int i = 0; i < this.tabList.size(); i++) {
                if (this.profileCache[i] == null) {
                    this.profileCache[i] = new GameProfile(
                            UUID.fromString(String.format("00000000-0000-%s-0000-000000000000", StringUtils.leftPad(String.valueOf(i), 2, '0'))),
                            " "
                    );
                }

                String text = tabList.get(i);
                GameProfile gameProfile = this.profileCache[i];
                IChatBaseComponent component = CraftChatMessage.fromStringOrNull(me.taison.wizardpractice.utilities.chat.StringUtils.color(text), false);

                PacketPlayOutPlayerInfo.PlayerInfoData playerInfoData = new PacketPlayOutPlayerInfo.PlayerInfoData(
                        gameProfile,
                        0,
                        DEFAULT_GAME_MODE,
                        component,
                        null
                );

                if (this.firstPacket) {
                    addPlayerList.add(playerInfoData);
                }

                updatePlayerList.add(playerInfoData);
            }

            if (this.firstPacket) {
                this.firstPacket = false;
            }

            PacketPlayOutPlayerInfo addPlayerPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a);
            addPlayerPacket.b().addAll(addPlayerList);
            packets.add(addPlayerPacket);

            PacketPlayOutPlayerInfo updatePlayerPacket = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.d);
            updatePlayerPacket.b().addAll(updatePlayerList);
            packets.add(updatePlayerPacket);

            boolean headerNotEmpty = !header.isEmpty();
            boolean footerNotEmpty = !footer.isEmpty();

            if (headerNotEmpty || footerNotEmpty) {
                IChatBaseComponent headerComponent = EMPTY_COMPONENT;
                IChatBaseComponent footerComponent = EMPTY_COMPONENT;

                if (headerNotEmpty) {
                    headerComponent = CraftChatMessage.fromStringOrNull(me.taison.wizardpractice.utilities.chat.StringUtils.color(header), true);
                }

                if (footerNotEmpty) {
                    footerComponent = CraftChatMessage.fromStringOrNull(me.taison.wizardpractice.utilities.chat.StringUtils.color(footer), true);
                }

                PacketPlayOutPlayerListHeaderFooter headerFooterPacket = new PacketPlayOutPlayerListHeaderFooter(headerComponent, footerComponent);
                packets.add(headerFooterPacket);
            }

            for (Packet<?> packet : packets) {
                ((CraftPlayer) player).getHandle().b.a(packet);
            }
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to send PlayerList for player " + player.getName(), exception);
        }
    }

    private void prepareCells() {
        tabList.replaceAll(entry -> entry
                .replace("{kills}", String.valueOf(user.getUserRanking(RankingType.KILLS).getRanking()))
                .replace("{deaths}", String.valueOf(user.getUserRanking(RankingType.DEATH).getRanking()))
                .replace("{nick}", user.getName())
        );

        rankingNames.forEach((rankingType, rankingName) -> {
            int count = (int) tabList.stream().filter(entry -> entry.contains(rankingName)).count();
            if (count > 0) {
                List<User> topUsers = WizardPractice.getSingleton().getRankingFactory().getTopUsers(count, rankingType);
                tabList.replaceAll(entry -> {
                    if (entry.contains(rankingName)) {
                        int rank = Integer.parseInt(entry.split("-")[2].replace("}", ""));
                        return rank <= topUsers.size() ? formatUserEntry(topUsers.get(rank - 1), rank, rankingType) : String.format("&7%d. Brak rankingu", rank);
                    }
                    return entry;
                });
            }
        });
    }

    private String formatUserEntry(User user, int rank, RankingType rankingType) {
        return String.format("&7%d. %s &6(%d)", rank, user.getName(), (Integer) user.getUserRanking(rankingType).getRanking());
    }

}
