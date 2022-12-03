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

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        WizardPractice.getSingleton().getStaticGuiFactory().getGameSelectorGui().open(player);
    }
}