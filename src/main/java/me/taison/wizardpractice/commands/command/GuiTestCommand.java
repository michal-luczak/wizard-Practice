package me.taison.wizardpractice.commands.command;

import me.taison.wizardpractice.commands.ICommandInfo;
import me.taison.wizardpractice.gui.gametypeselector.GameSelectorGui;
import me.taison.wizardpractice.utilities.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@ICommandInfo(command = "gui")
public class GuiTestCommand extends AbstractCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        new GameSelectorGui().open(player);
    }
}