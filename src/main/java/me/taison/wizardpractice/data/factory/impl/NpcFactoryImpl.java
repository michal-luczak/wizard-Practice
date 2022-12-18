package me.taison.wizardpractice.data.factory.impl;

import me.taison.wizardpractice.data.factory.NpcFactory;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.npc.NPC;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NpcFactoryImpl implements NpcFactory {

    private final List<NPC> NPCList;

    public NpcFactoryImpl() {
        this.NPCList = new ArrayList<>();
    }


    @Override
    public void createNPC(NPC npc) {
        NPCList.add(npc);
    }

    @Override
    public void removeNPC(NPC npc) {
        NPCList.remove(npc);
    }

    @Override
    public void spawnNPCs(Player player) {
        NPCList.forEach(NPC -> NPC.spawnNPC(player));
    }

    @Override
    public Optional<NPC> getByGameMapType(GameMapType gameMapType) {
        return NPCList.stream().filter(NPC -> NPC.getGameMapType() == gameMapType).findFirst();
    }

    @Override
    public Optional<NPC> getByUniqueId(UUID uuid) {
        return NPCList.stream().filter(NPC -> NPC.getUniqueId().equals(uuid)).findFirst();
    }
}
