package me.taison.wizardpractice;

import me.taison.wizardpractice.addons.impl.MagicChest;
import me.taison.wizardpractice.data.factory.*;
import me.taison.wizardpractice.data.factory.impl.*;
import me.taison.wizardpractice.data.storage.database.MySQLStorage;
import me.taison.wizardpractice.data.storage.database.impl.MySQLStorageImpl;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.impl.ArenaImpl;
import me.taison.wizardpractice.game.matchmakingsystem.Matchmaker;
import me.taison.wizardpractice.game.matchmakingsystem.matchmaker.MatchmakerImpl;
import me.taison.wizardpractice.game.task.QueueActionBarUpdateTask;
import me.taison.wizardpractice.game.task.RankingHologramUpdateTask;
import me.taison.wizardpractice.game.task.TablistUpdateTask;
import me.taison.wizardpractice.gui.gametypeselector.GameMapType;
import me.taison.wizardpractice.npc.NPC;
import me.taison.wizardpractice.service.Service;
import me.taison.wizardpractice.utilities.AbstractCommand;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public final class WizardPractice extends JavaPlugin {

    private static WizardPractice singleton;

    private UserFactory userFactory;

    private TeamFactory teamFactory;

    private ArenaFactory arenaFactory;

    private AddonFactory addonFactory;

    private RankingFactory rankingFactory;

    private HologramFactory hologramFactory;

    private MySQLStorage database;

    private Matchmaker matchmaker;

    private Location SPAWN_LOCATION;

    private QueueActionBarUpdateTask queueActionBarUpdateTask;

    private RankingHologramUpdateTask rankingHologramUpdateTask;

    private TablistUpdateTask tablistUpdateTask;

    private NPCFactory npcFactory;

    @Override
    public void onLoad(){
        singleton = this;
    }

    @Override
    public void onEnable() {
        getLogger().info("Practice plugin loading...");

        this.initializeFactories();
        this.initializeDatabase();
        this.initializeArenas();
        this.initializeTasks();
        this.initializeNPCs();
        this.initializeListeners();
        this.initializeCommands();

        this.matchmaker = new MatchmakerImpl(arenaFactory);

        //freeze time and weather
        getServer().getWorlds().forEach(world -> world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false));
        getServer().getWorlds().forEach(world -> world.setGameRule(GameRule.DO_WEATHER_CYCLE, false));
        getServer().getWorlds().forEach(world -> world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false));
        getServer().getWorlds().forEach(world -> world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN,  true));

        this.SPAWN_LOCATION = new Location(Bukkit.getServer().getWorld("world"), -2.5, 125, -57.5);

        getLogger().info("Practice plugin loading successfully.");
    }

    private void initializeCommands() {
        getLogger().info("Initializing commands..");
        for (Class<? extends AbstractCommand> clazz : new Reflections(getClass().getPackageName() + ".commands.command").getSubTypesOf(AbstractCommand.class)) {
            try {
                AbstractCommand playerCommand = clazz.getDeclaredConstructor().newInstance();
                getCommand(playerCommand.getCommandInfo().command()).setExecutor(playerCommand);
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeListeners() {
        getLogger().info("Initializing listeners..");
        for (Class<?> clazz : new Reflections(getClass().getPackageName() + ".listeners").getSubTypesOf(Listener.class)) {
            try {
                Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
                getServer().getPluginManager().registerEvents(listener, this);
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void initializeFactories() {
        getLogger().info("Initializing factories..");

        this.userFactory = new UserFactoryImpl(this);
        this.addonFactory = new AddonFactoryImpl(this);

        this.teamFactory = new TeamFactoryImpl(this);
        this.arenaFactory = new ArenaFactoryImpl(this);

        this.rankingFactory = new RankingFactoryImpl();

        this.hologramFactory = new HologramFactoryImpl(this);

        this.npcFactory = new NPCFactoryImpl();

        //Jakis glupi test nudzi mi sie
        MagicChest magicChest = new MagicChest(null, 0, "xd");
    }

    private void initializeArenas(){
        Arena arena1 = new ArenaImpl("Brazowa arena")
                .setWorld(Bukkit.getWorld("world"))
                .addSpawnLocation(235.5, 90, 1.5, 0, -90)
                .addSpawnLocation(167.5, 90, -0.5, 90, 0)
                .build();

        Arena arena2 = new ArenaImpl("Defaultowa arena")
                .setWorld(Bukkit.getWorld("world"))
                .addSpawnLocation(-34.5, 90, 200.5, -90, 0)
                .addSpawnLocation(30.5, 90, 202.5, 90, 0)
                .build();

        Arena arena3 = new ArenaImpl("Netherowa arena")
                .setWorld(Bukkit.getWorld("world"))
                .addSpawnLocation(431.5, 90, 1.5, 90, 0)
                .addSpawnLocation(366.5, 90, 202.5, -90, 0)
                .build();

        Arena arena4 = new ArenaImpl("Pustynna arena")
                .setWorld(Bukkit.getWorld("world"))
                .addSpawnLocation(631.5, 90, 0.5, 90, 0)
                .addSpawnLocation(566.5, 90, -1.5, -90, 0)
                .build();

        Arena arena5 = new ArenaImpl("Rozowa arena")
                .setWorld(Bukkit.getWorld("world"))
                .addSpawnLocation(952.5, 90, 163.5, 90, 0)
                .addSpawnLocation(887.5, 90, 161.5, -90, 0)
                .build();

        Arena arena6 = new ArenaImpl("Rozowa 3 sloty arena")
                .setWorld(Bukkit.getWorld("world"))
                .addSpawnLocation(896.5, 90, 409.5, -90, 0)
                .addSpawnLocation(935.5, 90, 392.5, 0, 0)
                .addSpawnLocation(961.5, 90, 411.5, 90, 0)
                .build();

        this.arenaFactory.register(arena1);
        this.arenaFactory.register(arena2);
        this.arenaFactory.register(arena3);
        this.arenaFactory.register(arena4);
        this.arenaFactory.register(arena5);
        this.arenaFactory.register(arena6);
    }

    private void initializeTasks(){
        this.queueActionBarUpdateTask = new QueueActionBarUpdateTask(this);
        this.queueActionBarUpdateTask.startActionBarUpdate();

        this.rankingHologramUpdateTask = new RankingHologramUpdateTask(this);
        this.rankingHologramUpdateTask.startRankingHologramUpdate();

        this.tablistUpdateTask = new TablistUpdateTask(this);
        this.tablistUpdateTask.startTablistUpdate();
    }

    private void initializeDatabase() {
        //Config later...
        this.database = new MySQLStorageImpl(this);
        this.database.open();
    }

    private void initializeNPCs() {
//        this.npcFactory = new NPCFactoryImpl();
//
//        NPC npc1 = new NPC.NpcBuilder()
//                .setGameMapType(GameMapType.CRYSTAL_PVP_DIAMOND)
//                .setName("&aCRYSTAL PVP DIAMOND")
//                .setLocation(new Location(Bukkit.getWorld("world"), 1.5, 124, -50.5))
//                .setSignature("eJZc0I+VdpYxjJRn4a7qcWpULbPKPweJSA/9Fcvkk76IhLQOViOp+c/a0dFB97n9AsH36pWjs+bmcDniV0FGAnXGeEmwlnPE2ANMK8LYUQAhtVC98mCa3xOYm8UYhtmSZXzkn5Kem4fK/bvZK4aHF1Tod/WwayGjeTiUvDCsjSmr3nN9x24sr56Y6MX837pP+0Xnx5WyE8fb/FIjgEMAWWQsfHlRFx4mAcZvDvi1As7V/jm/b+4xMEKpD+Z731UsdV5FoHZvhEHVOm6nac/YQh1IDlNg7laiHztiPECijjfah9Jeoia4w+2dnxJIVLs9dogdXLDANzmFIHrP9PxiMFzb88J5Uh93rqJPIl2RBF7vJN/d5zN5hSOUeIPe36+7zFo5T8zfGq/wwl1A9Q+0Uhrq0ThCQjZEXYdUyRAhGYBHSZgVRhDp5plS9vfjff5goDWhZP3GNFqkTklNuyiIGOny7SKOFhN9xJekPCmrNmVMD3M7FIgU6114CJX5Cth2lVqaUeTytF/9GCENgCkVw37JVClYpLitV8ApqlMTkDS0LDVRPZArf4BydXED1V0Eyhabk5Or7IrQSIPeH6BEse6tQPWUtZ1f7pgqGR3NJvdyeNmYpoynzooJq+QWlv2Ivm8LM7mu6rCDcVPaUlnD0fBnRLpcvnnHbC+zJg7V5DE=")
//                .setTexture("ewogICJ0aW1lc3RhbXAiIDogMTY2OTU4NDQ3OTYxMCwKICAicHJvZmlsZUlkIiA6ICJhZDQxZDc1NTg4YjQ0MmRjOWRiZDU1MDM3ZDUzY2Y4MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJfVGFpc29uXyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yZGNhMjdkNmFlOTI5YjU4ZDRlZTIzYWU3MjVhNzZiMmExZjA0ZDUyNjZhZjFlZDRkMTI1ZTU2ZWIwZTYyNzg1IgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMzQwYzBlMDNkZDI0YTExYjE1YThiMzNjMmE3ZTllMzJhYmIyMDUxYjI0ODFkMGJhN2RlZmQ2MzVjYTdhOTMzIgogICAgfQogIH0KfQ==")
//                .build();
//
//        NPC npc2 = new NPC.NpcBuilder()
//                .setGameMapType(GameMapType.POTION)
//                .setName("&aPOTION")
//                .setLocation(new Location(Bukkit.getWorld("world"), -0.5, 124, -50.5))
//                .setSignature("eJZc0I+VdpYxjJRn4a7qcWpULbPKPweJSA/9Fcvkk76IhLQOViOp+c/a0dFB97n9AsH36pWjs+bmcDniV0FGAnXGeEmwlnPE2ANMK8LYUQAhtVC98mCa3xOYm8UYhtmSZXzkn5Kem4fK/bvZK4aHF1Tod/WwayGjeTiUvDCsjSmr3nN9x24sr56Y6MX837pP+0Xnx5WyE8fb/FIjgEMAWWQsfHlRFx4mAcZvDvi1As7V/jm/b+4xMEKpD+Z731UsdV5FoHZvhEHVOm6nac/YQh1IDlNg7laiHztiPECijjfah9Jeoia4w+2dnxJIVLs9dogdXLDANzmFIHrP9PxiMFzb88J5Uh93rqJPIl2RBF7vJN/d5zN5hSOUeIPe36+7zFo5T8zfGq/wwl1A9Q+0Uhrq0ThCQjZEXYdUyRAhGYBHSZgVRhDp5plS9vfjff5goDWhZP3GNFqkTklNuyiIGOny7SKOFhN9xJekPCmrNmVMD3M7FIgU6114CJX5Cth2lVqaUeTytF/9GCENgCkVw37JVClYpLitV8ApqlMTkDS0LDVRPZArf4BydXED1V0Eyhabk5Or7IrQSIPeH6BEse6tQPWUtZ1f7pgqGR3NJvdyeNmYpoynzooJq+QWlv2Ivm8LM7mu6rCDcVPaUlnD0fBnRLpcvnnHbC+zJg7V5DE=")
//                .setTexture("ewogICJ0aW1lc3RhbXAiIDogMTY2OTU4NDQ3OTYxMCwKICAicHJvZmlsZUlkIiA6ICJhZDQxZDc1NTg4YjQ0MmRjOWRiZDU1MDM3ZDUzY2Y4MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJfVGFpc29uXyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yZGNhMjdkNmFlOTI5YjU4ZDRlZTIzYWU3MjVhNzZiMmExZjA0ZDUyNjZhZjFlZDRkMTI1ZTU2ZWIwZTYyNzg1IgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMzQwYzBlMDNkZDI0YTExYjE1YThiMzNjMmE3ZTllMzJhYmIyMDUxYjI0ODFkMGJhN2RlZmQ2MzVjYTdhOTMzIgogICAgfQogIH0KfQ==")
//                .build();
//
//        NPC npc3 = new NPC.NpcBuilder()
//                .setGameMapType(GameMapType.CRYSTAL_PVP_NETHERITE)
//                .setName("&aCRYSTAL PVP NETHERITE")
//                .setLocation(new Location(Bukkit.getWorld("world"), -2.5, 124, -50.5))
//                .setSignature("eJZc0I+VdpYxjJRn4a7qcWpULbPKPweJSA/9Fcvkk76IhLQOViOp+c/a0dFB97n9AsH36pWjs+bmcDniV0FGAnXGeEmwlnPE2ANMK8LYUQAhtVC98mCa3xOYm8UYhtmSZXzkn5Kem4fK/bvZK4aHF1Tod/WwayGjeTiUvDCsjSmr3nN9x24sr56Y6MX837pP+0Xnx5WyE8fb/FIjgEMAWWQsfHlRFx4mAcZvDvi1As7V/jm/b+4xMEKpD+Z731UsdV5FoHZvhEHVOm6nac/YQh1IDlNg7laiHztiPECijjfah9Jeoia4w+2dnxJIVLs9dogdXLDANzmFIHrP9PxiMFzb88J5Uh93rqJPIl2RBF7vJN/d5zN5hSOUeIPe36+7zFo5T8zfGq/wwl1A9Q+0Uhrq0ThCQjZEXYdUyRAhGYBHSZgVRhDp5plS9vfjff5goDWhZP3GNFqkTklNuyiIGOny7SKOFhN9xJekPCmrNmVMD3M7FIgU6114CJX5Cth2lVqaUeTytF/9GCENgCkVw37JVClYpLitV8ApqlMTkDS0LDVRPZArf4BydXED1V0Eyhabk5Or7IrQSIPeH6BEse6tQPWUtZ1f7pgqGR3NJvdyeNmYpoynzooJq+QWlv2Ivm8LM7mu6rCDcVPaUlnD0fBnRLpcvnnHbC+zJg7V5DE=")
//                .setTexture("ewogICJ0aW1lc3RhbXAiIDogMTY2OTU4NDQ3OTYxMCwKICAicHJvZmlsZUlkIiA6ICJhZDQxZDc1NTg4YjQ0MmRjOWRiZDU1MDM3ZDUzY2Y4MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJfVGFpc29uXyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yZGNhMjdkNmFlOTI5YjU4ZDRlZTIzYWU3MjVhNzZiMmExZjA0ZDUyNjZhZjFlZDRkMTI1ZTU2ZWIwZTYyNzg1IgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMzQwYzBlMDNkZDI0YTExYjE1YThiMzNjMmE3ZTllMzJhYmIyMDUxYjI0ODFkMGJhN2RlZmQ2MzVjYTdhOTMzIgogICAgfQogIH0KfQ==")
//                .build();
//
//        NPC npc4 = new NPC.NpcBuilder()
//                .setGameMapType(GameMapType.ELYTRA)
//                .setName("&aELYTRA")
//                .setLocation(new Location(Bukkit.getWorld("world"), -4.5, 124, -50.5))
//                .setSignature("eJZc0I+VdpYxjJRn4a7qcWpULbPKPweJSA/9Fcvkk76IhLQOViOp+c/a0dFB97n9AsH36pWjs+bmcDniV0FGAnXGeEmwlnPE2ANMK8LYUQAhtVC98mCa3xOYm8UYhtmSZXzkn5Kem4fK/bvZK4aHF1Tod/WwayGjeTiUvDCsjSmr3nN9x24sr56Y6MX837pP+0Xnx5WyE8fb/FIjgEMAWWQsfHlRFx4mAcZvDvi1As7V/jm/b+4xMEKpD+Z731UsdV5FoHZvhEHVOm6nac/YQh1IDlNg7laiHztiPECijjfah9Jeoia4w+2dnxJIVLs9dogdXLDANzmFIHrP9PxiMFzb88J5Uh93rqJPIl2RBF7vJN/d5zN5hSOUeIPe36+7zFo5T8zfGq/wwl1A9Q+0Uhrq0ThCQjZEXYdUyRAhGYBHSZgVRhDp5plS9vfjff5goDWhZP3GNFqkTklNuyiIGOny7SKOFhN9xJekPCmrNmVMD3M7FIgU6114CJX5Cth2lVqaUeTytF/9GCENgCkVw37JVClYpLitV8ApqlMTkDS0LDVRPZArf4BydXED1V0Eyhabk5Or7IrQSIPeH6BEse6tQPWUtZ1f7pgqGR3NJvdyeNmYpoynzooJq+QWlv2Ivm8LM7mu6rCDcVPaUlnD0fBnRLpcvnnHbC+zJg7V5DE=")
//                .setTexture("ewogICJ0aW1lc3RhbXAiIDogMTY2OTU4NDQ3OTYxMCwKICAicHJvZmlsZUlkIiA6ICJhZDQxZDc1NTg4YjQ0MmRjOWRiZDU1MDM3ZDUzY2Y4MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJfVGFpc29uXyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yZGNhMjdkNmFlOTI5YjU4ZDRlZTIzYWU3MjVhNzZiMmExZjA0ZDUyNjZhZjFlZDRkMTI1ZTU2ZWIwZTYyNzg1IgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMzQwYzBlMDNkZDI0YTExYjE1YThiMzNjMmE3ZTllMzJhYmIyMDUxYjI0ODFkMGJhN2RlZmQ2MzVjYTdhOTMzIgogICAgfQogIH0KfQ==")
//                .build();
//
//        NPC npc5 = new NPC.NpcBuilder()
//                .setGameMapType(GameMapType.NORMAL)
//                .setName("&aNORMAL")
//                .setLocation(new Location(Bukkit.getWorld("world"), -66.5, 124, -50.5))
//                .setSignature("eJZc0I+VdpYxjJRn4a7qcWpULbPKPweJSA/9Fcvkk76IhLQOViOp+c/a0dFB97n9AsH36pWjs+bmcDniV0FGAnXGeEmwlnPE2ANMK8LYUQAhtVC98mCa3xOYm8UYhtmSZXzkn5Kem4fK/bvZK4aHF1Tod/WwayGjeTiUvDCsjSmr3nN9x24sr56Y6MX837pP+0Xnx5WyE8fb/FIjgEMAWWQsfHlRFx4mAcZvDvi1As7V/jm/b+4xMEKpD+Z731UsdV5FoHZvhEHVOm6nac/YQh1IDlNg7laiHztiPECijjfah9Jeoia4w+2dnxJIVLs9dogdXLDANzmFIHrP9PxiMFzb88J5Uh93rqJPIl2RBF7vJN/d5zN5hSOUeIPe36+7zFo5T8zfGq/wwl1A9Q+0Uhrq0ThCQjZEXYdUyRAhGYBHSZgVRhDp5plS9vfjff5goDWhZP3GNFqkTklNuyiIGOny7SKOFhN9xJekPCmrNmVMD3M7FIgU6114CJX5Cth2lVqaUeTytF/9GCENgCkVw37JVClYpLitV8ApqlMTkDS0LDVRPZArf4BydXED1V0Eyhabk5Or7IrQSIPeH6BEse6tQPWUtZ1f7pgqGR3NJvdyeNmYpoynzooJq+QWlv2Ivm8LM7mu6rCDcVPaUlnD0fBnRLpcvnnHbC+zJg7V5DE=")
//                .setTexture("ewogICJ0aW1lc3RhbXAiIDogMTY2OTU4NDQ3OTYxMCwKICAicHJvZmlsZUlkIiA6ICJhZDQxZDc1NTg4YjQ0MmRjOWRiZDU1MDM3ZDUzY2Y4MSIsCiAgInByb2ZpbGVOYW1lIiA6ICJfVGFpc29uXyIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yZGNhMjdkNmFlOTI5YjU4ZDRlZTIzYWU3MjVhNzZiMmExZjA0ZDUyNjZhZjFlZDRkMTI1ZTU2ZWIwZTYyNzg1IgogICAgfSwKICAgICJDQVBFIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8yMzQwYzBlMDNkZDI0YTExYjE1YThiMzNjMmE3ZTllMzJhYmIyMDUxYjI0ODFkMGJhN2RlZmQ2MzVjYTdhOTMzIgogICAgfQogIH0KfQ==")
//                .build();
//
//        this.npcFactory.addNPC(npc1);
//        this.npcFactory.addNPC(npc2);
//        this.npcFactory.addNPC(npc3);
//        this.npcFactory.addNPC(npc4);
//        this.npcFactory.addNPC(npc5);
    }

    @Override
    public void onDisable() {
        getLogger().info("Practice plugin disabling...");

        Bukkit.getOnlinePlayers().forEach(player -> player.kick(Component.text("Restart practice, wejdz za chwilkę"), PlayerKickEvent.Cause.PLUGIN));

        this.addonFactory.deinitializeAddons();

        this.database.close();

        Service.shutdown();

        getLogger().info("Practice plugin disabled successfully. Goodbye!");
    }

    public static WizardPractice getSingleton() {
        return singleton;
    }

    public QueueActionBarUpdateTask getQueueActionBarUpdateTask() {
        return queueActionBarUpdateTask;
    }

    public ArenaFactory getArenaFactory() {
        return arenaFactory;
    }
    public Location getSpawnLocation() {
        return this.SPAWN_LOCATION;
    }
    public UserFactory getUserFactory() {
        return userFactory;
    }
    public AddonFactory getAddonFactory() {
        return addonFactory;
    }
    public Matchmaker getMatchmaker() {
        return matchmaker;
    }
    public TeamFactory getTeamFactory() {
        return teamFactory;
    }

    public RankingFactory getRankingFactory() {
        return rankingFactory;
    }

    public HologramFactory getHologramFactory() {
        return this.hologramFactory;
    }

    public RankingHologramUpdateTask getRankingHologramUpdateTask() {
        return rankingHologramUpdateTask;
    }

    public NPCFactory getNpcFactory() {
        return npcFactory;
    }
}
