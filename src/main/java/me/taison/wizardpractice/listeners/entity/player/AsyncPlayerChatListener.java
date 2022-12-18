package me.taison.wizardpractice.listeners.entity.player;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.taison.wizardpractice.utilities.chat.StringUtils;
import net.minecraft.network.chat.PlayerChatMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    //TODO change to newer version. This is depracated.
    @EventHandler
    public void handle(AsyncPlayerChatEvent e){
        String message = e.getPlayer().isOp() ? StringUtils.color(e.getMessage()) : e.getMessage();

        e.setFormat(StringUtils.color("&7"+ e.getPlayer().getDisplayName() + ": ") + message);
    }
}
