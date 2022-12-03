package me.taison.wizardpractice;

import me.taison.wizardpractice.data.factory.AddonFactory;
import me.taison.wizardpractice.data.factory.TeamFactory;
import me.taison.wizardpractice.data.factory.UserFactory;
import me.taison.wizardpractice.data.factory.impl.AddonFactoryImpl;
import me.taison.wizardpractice.data.factory.impl.TeamFactoryImpl;
import me.taison.wizardpractice.data.factory.impl.UserFactoryImpl;
import me.taison.wizardpractice.data.storage.IDatabase;
import me.taison.wizardpractice.data.storage.MySQLStorage;
import me.taison.wizardpractice.game.DuelManager;
import me.taison.wizardpractice.game.arena.Arena;
import me.taison.wizardpractice.game.queue.QueueDispatcher;
import me.taison.wizardpractice.service.Service;
import me.taison.wizardpractice.utilities.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public final class WizardPractice extends JavaPlugin {

    private static WizardPractice singleton;

    private UserFactory userFactory;

    private TeamFactory teamFactory;
    private AddonFactory addonFactory;

    private IDatabase database;

    private DuelManager duelManager;
    private QueueDispatcher queueDispatcher;

    @Override
    public void onLoad(){
        singleton = this;
    }

    @Override
    public void onEnable() {
        getLogger().info("Practice plugin loading...");

        this.duelManager = new DuelManager(new CopyOnWriteArraySet<>(this.initializeArenas()));
        this.queueDispatcher = new QueueDispatcher(duelManager);

        this.initializeFactories();
        this.initializeListeners();
        this.initializeCommands();

        this.database = new MySQLStorage();
        database.open();

        getLogger().info("Practice plugin loading successfully.");
    }

    private Set<Arena> initializeArenas() {
        return Set.of(Arena.PRZYKLADOWA_ARENA);
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
        //TODO Potrzeba wygodniejszego API do komend niz aktualnie jest.
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
    }

    @Override
    public void onDisable() {
        getLogger().info("Practice plugin disabling...");

        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("Restart"));

        this.userFactory.saveBoxUsers();
        this.addonFactory.deinitializeAddons();

        database.close();
        Service.shutdown();

        getLogger().info("Practice plugin disabled successfully. Goodbye!");
    }

    public static WizardPractice getSingleton() {
        return singleton;
    }

    public UserFactory getUserFactory() {
        return userFactory;
    }

    public AddonFactory getAddonFactory() {
        return addonFactory;
    }

    public DuelManager getDuelManager() {
        return duelManager;
    }
    public QueueDispatcher getQueueDispatcher() {
        return queueDispatcher;
    }
}
