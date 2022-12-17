package me.taison.wizardpractice;

import me.taison.wizardpractice.addons.impl.MagicChest;
import me.taison.wizardpractice.data.factory.*;
import me.taison.wizardpractice.data.factory.impl.*;
import me.taison.wizardpractice.data.storage.IDatabase;
import me.taison.wizardpractice.data.storage.MySQLStorage;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.arena.impl.ArenaImpl;
import me.taison.wizardpractice.game.matchmakingsystem.Matchmaker;
import me.taison.wizardpractice.game.matchmakingsystem.matchmaker.MatchmakerImpl;
import me.taison.wizardpractice.game.task.QueueActionBarUpdateTask;
import me.taison.wizardpractice.game.task.RankingHologramUpdateTask;
import me.taison.wizardpractice.game.task.TablistUpdateTask;
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

    private IDatabase database;

    private Matchmaker matchmaker;

    private Location SPAWN_LOCATION;

    private QueueActionBarUpdateTask queueActionBarUpdateTask;

    private RankingHologramUpdateTask rankingHologramUpdateTask;

    private TablistUpdateTask tablistUpdateTask;

    @Override
    public void onLoad(){
        singleton = this;
    }

    @Override
    public void onEnable() {
        getLogger().info("Practice plugin loading...");

        this.initializeFactories();
        this.initializeListeners();
        this.initializeCommands();
        this.initializeArenas();
        this.initializeTasks();

        this.matchmaker = new MatchmakerImpl(arenaFactory);

        this.database = new MySQLStorage();
        database.open();

        //freeze time and weather
        getServer().getWorlds().forEach(world -> world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false));
        getServer().getWorlds().forEach(world -> world.setGameRule(GameRule.DO_WEATHER_CYCLE, false));
        getServer().getWorlds().forEach(world -> world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN,  true));

        this.SPAWN_LOCATION = new Location(Bukkit.getServer().getWorld("world"), -2.5, 124, -53.5);

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

        //Jakis glupi test nudzi mi sie
        MagicChest magicChest = new MagicChest(null, 0, "xd");
    }

    private void initializeArenas(){
        Arena frostArena = new ArenaImpl("Zamrożona Kraina")
                .setWorld(Bukkit.getWorld("world"))
                .addSpawnLocation(-2.5, 90, -22.5, 0, 0)
                .addSpawnLocation(-3.5, 90, 20.5, 180, 0)
                .build();

        Arena candyArena = new ArenaImpl("Cukierkowy Las")
                .setWorld(Bukkit.getWorld("world"))
                .addSpawnLocation(-103.5, 109, -69.5, 0, 0)
                .addSpawnLocation(-84.5, 109, -46.5, 90, 0)
                .addSpawnLocation(-103.5, 109, -21.5, 180, 0)
                .build();

        this.arenaFactory.register(frostArena);
        this.arenaFactory.register(candyArena);

    }

    private void initializeTasks(){
        this.queueActionBarUpdateTask = new QueueActionBarUpdateTask(this);
        this.queueActionBarUpdateTask.startActionBarUpdate();

        this.rankingHologramUpdateTask = new RankingHologramUpdateTask(this);
        this.rankingHologramUpdateTask.startRankingHologramUpdate();

        this.tablistUpdateTask = new TablistUpdateTask(this);
        this.tablistUpdateTask.startTablistUpdate();
    }

    @Override
    public void onDisable() {
        getLogger().info("Practice plugin disabling...");

        Bukkit.getOnlinePlayers().forEach(player -> player.kick(Component.text("Restart practice, wejdz za chwilkę"), PlayerKickEvent.Cause.PLUGIN));

        this.userFactory.saveBoxUsers();
        this.addonFactory.deinitializeAddons();

        database.close();
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
}
