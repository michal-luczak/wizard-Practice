package me.taison.wizardpractice.commands;

import me.taison.wizardpractice.utilities.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@ICommandInfo(command = "spawn")
public class SpawnCommand extends AbstractCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        player.teleport(new Location(Bukkit.getWorlds().get(0), 0, 90, 0));
    }
}
