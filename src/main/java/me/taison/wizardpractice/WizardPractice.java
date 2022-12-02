package me.taison.wizardpractice;

import me.taison.wizardpractice.data.factory.AddonFactory;
import me.taison.wizardpractice.data.factory.PracticeUserFactory;
import me.taison.wizardpractice.data.storage.IDatabase;
import me.taison.wizardpractice.data.storage.MySQLStorage;
import me.taison.wizardpractice.duelsystem.Arena;
import me.taison.wizardpractice.duelsystem.DuelManager;
import me.taison.wizardpractice.service.Service;
import me.taison.wizardpractice.utilities.AbstractCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;

public final class WizardPractice extends JavaPlugin {

    private static WizardPractice singleton;
    private PracticeUserFactory practiceUserFactory;
    private AddonFactory addonFactory;
    private IDatabase database;
    private DuelManager duelManager;

    @Override
    public void onLoad(){
        singleton = this;
    }

    @Override
    public void onEnable() {
        getLogger().info("BoxPVP plugin loading...");

        this.initializeFactories();
        this.initializeListeners();
        this.initializeCommands();

        this.duelManager = new DuelManager(new CopyOnWriteArraySet<>(arenas()));

        this.database = new MySQLStorage();
        database.open();

        getLogger().info("BoxPVP plugin loading successfully.");
    }

    private HashSet<Arena> arenas() {
        HashSet<Arena> arenas = new HashSet<>();
        arenas.add(Arena.PRZYKLADOWA_ARENA);
        return arenas;
    }

    private void initializeCommands() {
        getLogger().info("Initializing commands..");
        for (Class<? extends AbstractCommand> clazz : new Reflections(getClass().getPackageName() + ".commands").getSubTypesOf(AbstractCommand.class)) {
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
    }

    private void initializeFactories() {
        getLogger().info("Initializing factories..");

        this.practiceUserFactory = new PracticeUserFactory(this);
        this.addonFactory = new AddonFactory(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("BoxPVP plugin disabling...");

        this.practiceUserFactory.saveBoxUsers();
        this.addonFactory.deinitializeAddons();

        database.close();
        Service.shutdown();

        getLogger().info("BoxPVP plugin disabled successfully. Goodbye!");
    }

    public static WizardPractice getSingleton() {
        return singleton;
    }

    public PracticeUserFactory getBoxUserFactory() {
        return practiceUserFactory;
    }

    public AddonFactory getAddonFactory() {
        return addonFactory;
    }

    public DuelManager getDuelManager() {
        return duelManager;
    }
}
