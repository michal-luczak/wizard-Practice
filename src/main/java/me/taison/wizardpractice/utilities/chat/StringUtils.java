package me.taison.wizardpractice.utilities.chat;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static String color(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(List<String> list) {
        List<String> colored = new ArrayList<>();

        for (String s : list) {
            colored.add(color(s));
        }
        return colored;
    }
}
