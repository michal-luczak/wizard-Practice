package me.taison.wizardpractice.data.user.impl.ranking;

import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserDeathRanking;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserKillsRanking;
import me.taison.wizardpractice.data.user.impl.ranking.types.UserPointsRanking;

public enum RankingType {

    DEFEATED_PLAYERS,
    POINTS,
    DEATHS;

    public AbstractRanking<?> getFor(User user){
        return switch (this) {
            case DEFEATED_PLAYERS -> new UserKillsRanking(user, this);
            case POINTS -> new UserPointsRanking(user, this);
            case DEATHS -> new UserDeathRanking(user, this);
        };
    }
    public boolean isMatchingType(AbstractRanking<?> ranking) {
        return switch (this) {
            case DEFEATED_PLAYERS -> ranking instanceof UserKillsRanking;
            case DEATHS -> ranking instanceof UserDeathRanking;
            case POINTS -> ranking instanceof UserPointsRanking;
        };
    }

}
