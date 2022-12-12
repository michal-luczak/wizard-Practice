package me.taison.wizardpractice.data.factory;

import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.UserRanking;

import java.util.List;

public interface RankingFactory {

    void update(User user);

    int getPosition(User user);

    User getUserByIndex(int index);

    List<User> getUsersInRange(int minScore, int maxScore);

    List<User> getTopUsers(int n);
}
