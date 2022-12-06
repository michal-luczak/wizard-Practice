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
            player.sendMessage(StringUtils.color("&a/zapros <nick> - zaprasza gracza do teamu"));
            player.sendMessage(StringUtils.color("&a/dolacz <nick> - dołącza do teamu (jeżeli posiadasz zaproszenie)"));
            player.sendMessage(StringUtils.color("&a/wyrzuc <nick> - wyrzuca gracza z teamu."));
            player.sendMessage(StringUtils.color("&a/usun - czyści twoją drużynę"));
            return;
        }

        UserFactory userFactory = WizardPractice.getSingleton().getUserFactory();

        if (args[0].equals("zapros")) {
            if (args.length == 1) {
                player.sendMessage(StringUtils.color("&cPodaj nick gracza!"));
                return;
            }

            Player playerToInvite = Bukkit.getPlayer(args[1]);
            if (playerToInvite == null) {
                player.sendMessage(StringUtils.color("&cNie ma takiego gracza na serwerze!"));
                return;
            }

            userFactory.getByUniqueId(playerToInvite.getUniqueId()).ifPresent(user -> {
                userFactory.getByUniqueId(player.getUniqueId()).ifPresent(playerUser -> {
                    if(!playerUser.getTeam().getLeader().equals(playerUser)){
                        player.sendMessage(StringUtils.color("&cNie mozesz zarzadzac druzyna nie bedac liderem."));
                        return;
                    }
                    playerUser.getTeam().invitePlayer(user);
                });
            });
        } else if (args[0].equals("dolacz")) {
            if (args.length == 1) {
                player.sendMessage(StringUtils.color("&cPodaj nazwe teamu!"));
                return;
            }
            Player leader = Bukkit.getPlayer(args[1]);
            if(leader == null) {
                player.sendMessage(StringUtils.color("&cNie ma takiego teamu!"));
                return;
            }

            userFactory.getByUniqueId(leader.getUniqueId()).ifPresent(leaderUser -> {
                Team team = leaderUser.getTeam();

                userFactory.getByUniqueId(player.getUniqueId()).ifPresent(invitedUser -> {
                    if(!team.getInvitations().asMap().containsKey(invitedUser)){
                        player.sendMessage(StringUtils.color("&cNie masz zaproszenia do tego teamu!"));
                        return;
                    }
                    team.join(invitedUser);
                });

            });
        } else if (args[0].equals("wyrzuc")){
            if (args.length == 1) {
                player.sendMessage(StringUtils.color("&cPodaj nazwe gracza do wyrzucenia"));
                return;
            }
            Player playerToKick = Bukkit.getPlayer(args[1]);
            if(playerToKick == null) {
                player.sendMessage(StringUtils.color("&cGracz do wyrzucenia nie jest na serwerze."));
                return;
            }
            userFactory.getByUniqueId(playerToKick.getUniqueId()).ifPresent(user -> userFactory.getByUniqueId(player.getUniqueId()).ifPresent(playerUser -> {
                if(!playerUser.getTeam().getLeader().equals(playerUser)){
                    player.sendMessage(StringUtils.color("&cNie mozesz zarzadzac druzyna nie bedac liderem."));
                    return;
                }
                if(!user.getTeam().equals(playerUser.getTeam())){
                    player.sendMessage(StringUtils.color("&cTen gracz nie jest w twoim teamie."));
                    return;
                }
                user.getTeam().kickPlayer(user);
            }));
        } else if (args[0].equals("usun")) {
            userFactory.getByUniqueId(player.getUniqueId()).ifPresent(playerUser -> {
                if (!playerUser.getTeam().getLeader().equals(playerUser)) {
                    player.sendMessage(StringUtils.color("&cNie mozesz zarzadzac druzyna nie bedac liderem."));
                    return;
                }
                playerUser.getTeam().disband();
            });
        }

        //new PartyGui().open(player);
    }
}