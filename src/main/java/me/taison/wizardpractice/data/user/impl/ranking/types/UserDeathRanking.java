package me.taison.wizardpractice.data.user.impl.ranking.types;

import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.AbstractRanking;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;

public class UserDeathRanking extends AbstractRanking<Integer> {
    private int deaths;

    public UserDeathRanking(User user, RankingType rankingType) {
        super(user, rankingType);
    }

    public Integer getRanking() {
        return this.deaths;
    }

    public void addDeath() {
        this.deaths += 1;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }
}
