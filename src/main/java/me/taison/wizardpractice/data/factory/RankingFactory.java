package me.taison.wizardpractice.data.factory;

import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;

import java.util.List;

public interface RankingFactory {

    int getPosition(User user, RankingType rankingType);

    User getUserByIndex(int index);

    List<User> getUsersInRange(int minScore, int maxScore);

    void update(User user, RankingType rankingType);

    List<User> getTopUsers(int n, RankingType rankingType);
}
