package me.taison.wizardpractice.listeners;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.TeamFactory;
import me.taison.wizardpractice.data.factory.UserFactory;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.TeamImpl;
import me.taison.wizardpractice.data.user.impl.UserImpl;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import me.taison.wizardpractice.utilities.items.VariousItems;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handle(PlayerJoinEvent e){
        e.getPlayer().getInventory().clear();
        e.getPlayer().getInventory().setItem(4, VariousItems.FEATHER_ITEM);

        UserFactory userFactory = WizardPractice.getSingleton().getUserFactory();
        TeamFactory teamFactory = WizardPractice.getSingleton().getTeamFactory();

        User user = new UserImpl(e.getPlayer().getUniqueId(), e.getPlayer().getName());
        Team team = new TeamImpl(user);

        userFactory.registerUser(user);
        teamFactory.register(team);

        user.setTeam(team);
        team.setLeader(user);

        e.joinMessage(Component.text(StringUtils.color("&a[+] " + e.getPlayer().getName())));
    }

}
