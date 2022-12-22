package me.taison.wizardpractice.commands.command;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.commands.ICommandInfo;
import me.taison.wizardpractice.utilities.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@ICommandInfo(command = "lobby", permission = "lobby")
public class LobbyCommand extends AbstractCommand {

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF("lobby");
            ((Player) sender).sendPluginMessage(WizardPractice.getPlugin(WizardPractice.class), "BungeeCord", b.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

