package me.taison.wizardpractice.utilities.time;

import me.taison.wizardpractice.WizardPractice;

import java.time.Duration;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {

    public static long parseTime(String string) {
        if (string == null || string.isEmpty()) {
            return 0;
        }

        Pattern pattern = Pattern.compile("(\\d+)([dhms])");
        Matcher matcher = pattern.matcher(string);

        long time = 0;
        while (matcher.find()) {
            long value = Long.parseLong(matcher.group(1));
            char type = matcher.group(2).charAt(0);

            switch (type) {
                case 'd' -> time += value * 86400000L;
                case 'h' -> time += value * 3600000L;
                case 'm' -> time += value * 60000L;
                case 's' -> time += value * 1000L;
            }
        }

        return time;
    }
    public static String getDurationBreakdown(long millis) {
        if (millis == 0) {
            return "0";
        }

        Duration duration = Duration.ofMillis(millis);

        long days = duration.toDays();
        duration = duration.minusDays(days);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);

        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);

        long seconds = duration.getSeconds();

        String result = "";
        if (days > 0) {
            result += String.format("%d dni ", days);
        }
        if (hours > 0) {
            result += String.format("%d godz. ", hours);
        }
        if (minutes > 0) {
            result += String.format("%d min. ", minutes);
        }
        if (seconds > 0) {
            result += String.format("%d sek. ", seconds);
        }

        return result.trim();
    }



}
