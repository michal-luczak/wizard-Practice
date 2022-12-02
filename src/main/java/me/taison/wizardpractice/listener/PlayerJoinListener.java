package me.taison.wizardpractice.listener;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.PracticeUserFactory;
import me.taison.wizardpractice.data.impl.PracticeUser;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handle(PlayerJoinEvent e){
        PracticeUserFactory factory = WizardPractice.getSingleton().getBoxUserFactory();

        //if(!e.getPlayer().hasPlayedBefore()) { TODO na razie zakomentowane bo nie ma bazy danych. Z bazą danych ci co byli juz na serwerze beda w pamięci.
            factory.registerUser(new PracticeUser(e.getPlayer().getUniqueId(), e.getPlayer().getName()));

            //Bukkit.broadcastMessage(StringUtils.color("&aWitamy gracza " + e.getPlayer() + " po raz pierwszy na naszym serwerze!"));
        e.setJoinMessage(StringUtils.color("&a[+] " + e.getPlayer().getName()));
        //}
    }

}
