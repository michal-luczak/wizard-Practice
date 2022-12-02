package me.taison.wizardpractice.commands.command;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.commands.ICommandInfo;
import me.taison.wizardpractice.data.factory.PracticeUserFactory;
import me.taison.wizardpractice.gui.gametypeselector.GameSelectorGui;
import me.taison.wizardpractice.utilities.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@ICommandInfo(command = "gui")
public class GuiTestCommand extends AbstractCommand {

    private final PracticeUserFactory practiceUserFactory = WizardPractice.getSingleton().getBoxUserFactory();

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        var practiceUser = practiceUserFactory.getUserByUniqueIdentifier(player.getUniqueId()).orElseThrow(IllegalStateException::new);

        new GameSelectorGui(practiceUser).open(player);
    }
}