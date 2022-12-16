package me.taison.wizardpractice.data.user.impl.ranking.types;

import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.AbstractRanking;

public class UserKillsRanking extends AbstractRanking<Integer> {

    private int kills;

    public UserKillsRanking(User user) {
        super(user);
    }

    @Override
    public Integer getRanking() {
        return this.kills;
    }

    public void addKill(){
        this.kills += 1;
    }

    public void setKills(int kills){
        this.kills = kills;
    }

}
