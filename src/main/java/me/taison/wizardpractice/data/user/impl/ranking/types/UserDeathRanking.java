package me.taison.wizardpractice.data.user.impl.ranking.types;

import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.AbstractRanking;

public class UserDeathRanking extends AbstractRanking<Integer> {
    private int deaths;

    public UserDeathRanking(User user) {
        super(user);
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
