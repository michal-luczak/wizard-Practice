package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.PracticeUserFactory;
import me.taison.wizardpractice.data.impl.PracticeUser;
import me.taison.wizardpractice.duelsystem.DuelManager;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import me.taison.wizardpractice.utilities.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {

    private final DuelManager duelManager = WizardPractice.getSingleton().getDuelManager();

    @EventHandler
    public void handle(PlayerJoinEvent e){

        e.getPlayer().getInventory().setItem(4, duelManager.getFeather());

        System.out.println(1);

        PracticeUserFactory factory = WizardPractice.getSingleton().getBoxUserFactory();

        //if(!e.getPlayer().hasPlayedBefore()) { TODO na razie zakomentowane bo nie ma bazy danych. Z bazą danych ci co byli juz na serwerze beda w pamięci.
            factory.registerUser(new PracticeUser(e.getPlayer().getUniqueId(), e.getPlayer().getName()));

            //Bukkit.broadcastMessage(StringUtils.color("&aWitamy gracza " + e.getPlayer() + " po raz pierwszy na naszym serwerze!"));
        e.setJoinMessage(StringUtils.color("&a[+] " + e.getPlayer().getName()));
        //}
    }

}
