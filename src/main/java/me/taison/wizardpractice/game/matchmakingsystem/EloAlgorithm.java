package me.taison.wizardpractice.game.matchmakingsystem;

import me.taison.wizardpractice.data.user.User;

public class EloAlgorithm {

    private final static int K = 32;
    private final static int DEVIDER = 400;
    private final static int POWER = 10;

    public static void updateRanking(User killer, User victim) {

        // Updating the Elo Ratings
        killer.getUserRanking().addPoints(calculateRanking(killer, victim)[0]);
        victim.getUserRanking().removePoints(calculateRanking(killer, victim)[1]);
    }

    public static int[] calculateRanking(User potencialKiller, User potencialVictim) {
        int killerRaking = potencialKiller.getUserRanking().getPoints();
        int victimRanking = potencialVictim.getUserRanking().getPoints();

        // To calculate the Winning probability of killer
        float probability1 = 1.0f / (1 + (float) (Math.pow(POWER, ((double) killerRaking - victimRanking) / DEVIDER)));
        float probability2 = 1.0f / (1 + (float) (Math.pow(POWER, ((double) victimRanking - killerRaking) / DEVIDER)));

        return new int[]{
                //potentially points to add
                Math.round(K * (1 - probability1)),

                //potentially points to remove
                Math.round(K * (1 - probability2))
        };
    }
}
