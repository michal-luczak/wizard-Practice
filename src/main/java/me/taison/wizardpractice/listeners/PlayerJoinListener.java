package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.UserFactory;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.impl.TeamImpl;
import me.taison.wizardpractice.data.user.impl.UserImpl;
import me.taison.wizardpractice.game.DuelManager;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final DuelManager duelManager = WizardPractice.getSingleton().getDuelManager();

    @EventHandler
    public void handle(PlayerJoinEvent e){

        e.getPlayer().getInventory().setItem(4, duelManager.getFeather());

        WizardPractice.getSingleton().getTeamFactory().register(new TeamImpl(new UserImpl(e.getPlayer().getUniqueId(), e.getPlayer().getName())));

        System.out.println(1);

        UserFactory factory = WizardPractice.getSingleton().getUserFactory();

        //if(!e.getPlayer().hasPlayedBefore()) { TODO na razie zakomentowane bo nie ma bazy danych. Z bazą danych ci co byli juz na serwerze beda w pamięci.
            factory.registerUser(new UserImpl(e.getPlayer().getUniqueId(), e.getPlayer().getName()));

            //Bukkit.broadcastMessage(StringUtils.color("&aWitamy gracza " + e.getPlayer() + " po raz pierwszy na naszym serwerze!"));
        e.setJoinMessage(StringUtils.color("&a[+] " + e.getPlayer().getName()));
        //}
    }

}
