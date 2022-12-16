package me.taison.wizardpractice.utilities;

import me.taison.wizardpractice.commands.ICommandInfo;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractCommand implements CommandExecutor {

    private final ICommandInfo commandInfo;

    public ICommandInfo getCommandInfo() {
        return commandInfo;
    }

    public AbstractCommand(){
        commandInfo = getClass().getDeclaredAnnotation(ICommandInfo.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender.hasPermission(commandInfo.permission()))){
            sender.sendMessage(StringUtils.color("&cNie posiadasz uprawnien do tej komendy! (" + commandInfo.permission() + ")"));
            return true;
        }
        if (!(sender instanceof Player) && commandInfo.requirePlayer()) {
            sender.sendMessage(StringUtils.color("&cTylko gracz może użyć tej komendy!"));
            return true;
        }
        try{
            this.onExecute(sender, args);
        }
        catch (Exception ex){
            sender.sendMessage(StringUtils.color("&4Wewnetrzny blad serwera: zglos to do administratora!"));
            ex.printStackTrace();
        }
        return true;
    }

    public abstract void onExecute(CommandSender sender, String[] args);
}