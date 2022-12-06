package me.taison.wizardpractice.utilities.random;

import org.apache.commons.lang3.Validate;

import java.util.Random;

public final class RandomUtils {

    private static final Random rand = new Random();

    public static int getRandInt(int min, int max) throws IllegalArgumentException {
        Validate.isTrue(max >= min, "Max can't be smaller than min!");
        return rand.nextInt(max - min + 1) + min;
    }

    public static double getRandDouble(double min, double max) throws IllegalArgumentException {
        Validate.isTrue(max >= min, "Max can't be smaller than min!");
        return rand.nextDouble() * (max - min) + min;
    }

    public static float getRandFloat(float min, float max) throws IllegalArgumentException {
        Validate.isTrue(max >= min, "Max can't be smaller than min!");
        return rand.nextFloat() * (max - min) + min;
    }

    public static int getNextInt(int a){
        return rand.nextInt(a);
    }

    public static boolean getChance(double chance) {
        return (chance >= 100) || (chance >= getRandDouble(0, 100));
    }

}