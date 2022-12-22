package me.taison.wizardpractice.commands.command;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.commands.ICommandInfo;
import me.taison.wizardpractice.utilities.AbstractCommand;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@ICommandInfo(command = "spawn", permission = "spawn")
public class SpawnCommand extends AbstractCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        player.teleport(WizardPractice.getSingleton().getSpawnLocation());

        player.sendMessage(Component.text(StringUtils.color("&aPrzeteleportowano.")));
    }
}
