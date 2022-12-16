package me.taison.wizardpractice.data.user.impl.ranking.elo;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserPointsRanking;

public class EloAlgorithm {

    private final static int K = 32;
    private final static int DIVIDER = 400;
    private final static int POWER = 10;

    public static int[] updateRanking(User killer, User victim) {

        int[] ranking = calculateRanking(killer, victim);

        // Updating the Elo Ratings
        ((UserPointsRanking) killer.getUserRanking(RankingType.POINTS)).addPoints(ranking[0]);
        ((UserPointsRanking) victim.getUserRanking(RankingType.POINTS)).subPoints(ranking[0]);

        WizardPractice.getSingleton().getRankingFactory().update(victim, RankingType.POINTS);
        WizardPractice.getSingleton().getRankingFactory().update(killer, RankingType.POINTS);

        return ranking;
    }

    public static int[] calculateRanking(User potentialKiller, User potentialVictim) {
        int killerRaking = (Integer) potentialKiller.getUserRanking(RankingType.POINTS).getRanking();
        int victimRanking = (Integer) potentialVictim.getUserRanking(RankingType.POINTS).getRanking();

        float probability1 = 1.0f / (1 + (float) (Math.pow(POWER, ((double) killerRaking - victimRanking) / DIVIDER)));
        float probability2 = 1.0f / (1 + (float) (Math.pow(POWER, ((double) victimRanking - killerRaking) / DIVIDER)));

        return new int[]{
                Math.round(K * (1 - probability1)),
                Math.round(K * (1 - probability2))
        };
    }
}
