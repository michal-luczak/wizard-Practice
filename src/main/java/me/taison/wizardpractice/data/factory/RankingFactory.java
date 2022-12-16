package me.taison.wizardpractice.data.factory;

import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.AbstractRanking;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;

import java.util.List;

public interface RankingFactory {

    int getPosition(User user, RankingType rankingType);

    User getUserByIndex(int index);

    //Metoda do turniejowego wyboru gry lub powiedzmy w przyszlosci jak bedzie gralo bardzo duzo osob to zeby dobieralo odpowiednich przeciwnikow.
    List<User> getUsersInRange(RankingType rankingType, int minScore, int maxScore);

    void update(User user, RankingType rankingType);

    void addRanking(AbstractRanking<?> ranking);

    List<User> getTopUsers(int n, RankingType rankingType);
}
