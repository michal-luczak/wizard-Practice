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

@ICommandInfo(command = "top")
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
        if (topUsers.isEmpty()) {
            player.sendMessage(Component.text(StringUtils.color("&cBrak topowych użytkowników dla podanego typu rankingu.")));
            return;
        }

        topUsers.forEach(user -> {
            AbstractRanking<?> ranking = user.getUserRanking(rankingType);

            player.sendMessage(Component.text(StringUtils.color(
                    String.format("&a%s #%d (%d)", user.getName(), ranking.getPosition(), (Integer) ranking.getRanking())
            )));
        });
    }

    public RankingType getFromString(String rankingType){
        Validate.notEmpty(rankingType, "RankingType cannot be empty!");

        return switch (rankingType) {
            case "punkty" -> RankingType.POINTS;
            case "smierci" -> RankingType.DEATHS;
            case "zabicia" -> RankingType.DEFEATED_PLAYERS;
            default -> null;
        };
    }
}