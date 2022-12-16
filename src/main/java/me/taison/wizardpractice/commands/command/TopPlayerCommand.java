package me.taison.wizardpractice.commands.command;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.commands.ICommandInfo;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;
import me.taison.wizardpractice.utilities.AbstractCommand;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@ICommandInfo(command = "top")
public class TopPlayerCommand extends AbstractCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if(args.length == 0){
            player.sendMessage(Component.text(StringUtils.color("&cPoprawne uzycie: /top <zabicia/punkty/smierci>")));
            return;
        }
        switch (args[0]) {
            case "punkty" -> {
                List<User> topPoints = WizardPractice.getSingleton().getRankingFactory().getTopUsers(10, RankingType.POINTS);
                topPoints.forEach(user -> {
                    player.sendMessage(Component.text(StringUtils.color("&a" + user.getName() + " #" + user.getUserRanking(RankingType.POINTS).getPosition() + "(" + user.getUserRanking(RankingType.POINTS).getRanking() + ")")));
                });
            }
            case "smierci" -> {
                List<User> topDeaths = WizardPractice.getSingleton().getRankingFactory().getTopUsers(10, RankingType.DEATHS);
                topDeaths.forEach(user -> {
                    player.sendMessage(Component.text(StringUtils.color("&a" + user.getName() + " #" + user.getUserRanking(RankingType.DEATHS).getPosition() + "("+ user.getUserRanking(RankingType.DEATHS).getRanking() + ")")));
                });
            }
            default -> {
                List<User> topKills = WizardPractice.getSingleton().getRankingFactory().getTopUsers(10, RankingType.DEFEATED_PLAYERS);
                topKills.forEach(user -> {
                    player.sendMessage(Component.text(StringUtils.color("&a" + user.getName() + " #" + user.getUserRanking(RankingType.DEFEATED_PLAYERS).getPosition() + "("+ user.getUserRanking(RankingType.DEFEATED_PLAYERS).getRanking() + ")")));
                });
            }
        }
    }
}