package me.taison.wizardpractice.data.factory.impl;

import me.taison.wizardpractice.data.factory.NPCFactory;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.npc.NPC;
import org.bukkit.entity.Player;

import java.util.*;

public class NPCFactoryImpl implements NPCFactory {

    private final Map<Integer, NPC> NPCList;

    public NPCFactoryImpl() {
        this.NPCList = new HashMap<>();
    }


    @Override
    public void addNPC(NPC npc) {
        NPCList.put(npc.getEntityId(), npc);
    }


    @Override
    public void spawnNPCs(Player player) {
        NPCList.values().forEach(npc -> npc.spawnNPC(player));
    }

    @Override
    public Optional<NPC> getByGameMapType(GameMapType gameMapType) {
        return NPCList.values().stream().filter(NPC -> NPC.getGameMapType() == gameMapType).findFirst();
    }

    @Override
    public Optional<NPC> getByEntityId(int id) {
        return Optional.of(NPCList.get(id));
    }
}
