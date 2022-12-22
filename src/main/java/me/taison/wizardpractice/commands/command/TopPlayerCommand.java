package me.taison.wizardpractice.commands.command;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.commands.ICommandInfo;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.AbstractRanking;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;
import me.taison.wizardpractice.utilities.AbstractCommand;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang3.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@ICommandInfo(command = "top", permission = "top")
public class TopPlayerCommand extends AbstractCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Component.text(StringUtils.color("&cPoprawne użycie: /top <zabicia/punkty/smierci>")));
            return;
        }

        RankingType rankingType = this.getFromString(args[0]);
        if(rankingType == null){
            player.sendMessage(Component.text(StringUtils.color("&cPoprawne użycie: /top <zabicia/punkty/smierci>")));
            return;
        }

        List<User> topUsers = WizardPractice.getSingleton().getRankingFactory().getTopUsers(10, rankingType);

        player.sendMessage(Component.text(StringUtils.color("&2Lista najlepszych w rankingu " + args[0])));
        for (int i = 0; i < 10; i++) {
            if (i >= topUsers.size()) {
                player.sendMessage(Component.text(StringUtils.color("&a" + (i + 1) + ". -")));
            } else {
                User user = topUsers.get(i);
                AbstractRanking<?> ranking = user.getUserRanking(rankingType);

                player.sendMessage(Component.text(StringUtils.color(
                        String.format("&a%d. %s #%d (%d)", i + 1, user.getName(), ranking.getPosition(), (Integer) ranking.getRanking())
                )));
            }
        }
    }

    public RankingType getFromString(String rankingType){
        Validate.notEmpty(rankingType, "RankingType cannot be empty!");

        return switch (rankingType) {
            case "punkty" -> RankingType.POINTS;
            case "smierci" -> RankingType.DEATH;
            case "zabicia" -> RankingType.KILLS;
            default -> null;
        };
    }
}