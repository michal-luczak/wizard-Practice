package me.taison.wizardpractice.data.factory;

import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.npc.NPC;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface NpcFactory {

    void createNPC(NPC npc);

    void removeNPC(NPC npc);

    void spawnNPCs(Player player);

    Optional<NPC> getByGameMapType(GameMapType gameMapType);

    Optional<NPC> getByUniqueId(UUID uuid);
}
