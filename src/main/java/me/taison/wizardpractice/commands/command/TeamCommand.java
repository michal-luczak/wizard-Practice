package me.taison.wizardpractice.commands.command;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.commands.ICommandInfo;
import me.taison.wizardpractice.data.factory.TeamFactory;
import me.taison.wizardpractice.data.factory.UserFactory;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.gui.gametypeselector.GameSelectorGui;
import me.taison.wizardpractice.gui.party.PartyGui;
import me.taison.wizardpractice.utilities.AbstractCommand;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@ICommandInfo(command = "druzyna")
public class TeamCommand extends AbstractCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(StringUtils.color("&a/zapros {nick} -> zaprasza gracza do teamu"));
            player.sendMessage(StringUtils.color("&a/dolacz {nick} -> dołącza do teamu (jeżeli posiadasz zaproszenie)"));
            return;
        }
        if (args[0].equals("zapros")) {
            if (args.length == 1) {
                player.sendMessage(StringUtils.color("&cPodaj nick gracza!"));
                return;
            }
            if (Bukkit.getPlayer(args[1]) == null) {
                player.sendMessage(StringUtils.color("&cNie ma takiego gracza na serwerze!"));
                return;
            }
            Player playerToInvite = Bukkit.getPlayer(args[1]);
            UserFactory userFactory = WizardPractice.getSingleton().getUserFactory();
            if (userFactory.getByUniqueId(playerToInvite.getUniqueId()).isPresent()) {
                User user = userFactory.getByUniqueId(playerToInvite.getUniqueId()).get();
                user.getTeam().invitePlayer(user);
            }
        } else if (args[0].equals("dolacz")) {
            if (args.length == 1) {
                player.sendMessage(StringUtils.color("&cPodaj nazwe teamu!"));
                return;
            }
            if (Bukkit.getPlayer(args[1]) == null) {
                player.sendMessage(StringUtils.color("&cNie masz zaproszenia do tego teamu!"));
                return;
            }
            Player leader = Bukkit.getPlayer(args[1]);
            UserFactory userFactory = WizardPractice.getSingleton().getUserFactory();
            if (userFactory.getByUniqueId(leader.getUniqueId()).isPresent()) {
                Team team = userFactory.getByUniqueId(leader.getUniqueId()).get().getTeam();
                if (userFactory.getByUniqueId(player.getUniqueId()).isPresent()) {
                    User user = userFactory.getByUniqueId(player.getUniqueId()).get();
                    team.join(user);
                }
            }
        }

        //new PartyGui().open(player);
    }
}