package me.taison.wizardpractice.utilities.chat;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

    public static List<String> findAndReplace(List<String> source, List<String> searchStrings, Object... replacementStrings){
        IntStream.range(0, source.size()).forEach(i -> {
            String sourceLine = source.get(i);
            if (sourceLine.equalsIgnoreCase(searchStrings.get(i))) {
                source.set(i, String.valueOf(replacementStrings[i]));
            }
        });
        return source;
    }

    public static List<String> findAndReplace(List<String> list, String searchString, String replacementString){
        int bound = list.size();
        for (int i = 0; i < bound; i++) {
            if (list.get(i).contains(searchString)) {
                list.set(i, list.get(i).replace(searchString, replacementString));
            }
        }
        return list;
    }
}
