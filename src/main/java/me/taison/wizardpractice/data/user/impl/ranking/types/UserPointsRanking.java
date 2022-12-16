package me.taison.wizardpractice.data.user.impl.ranking.types;

import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.AbstractRanking;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;

public class UserPointsRanking extends AbstractRanking<Integer> {

    private int points;

    public UserPointsRanking(User user, RankingType rankingType) {
        super(user, rankingType);

        this.points = 500; //Domyslna ilosc punktow.
    }

    @Override
    public Integer getRanking() {
        return this.points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public void subPoints(int points) {
        this.points -= points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}