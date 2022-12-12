package me.taison.wizardpractice.data.factory.impl;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.factory.RankingFactory;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.UserRanking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingFactoryImpl implements RankingFactory {

    private final List<UserRanking> rankings = new ArrayList<>();

    private final WizardPractice wizardPractice;

    public RankingFactoryImpl(WizardPractice wizardPractice) {
        this.wizardPractice = wizardPractice;
    }

    @Override
    public void update(User user) {
        UserRanking ranking = user.getUserRanking();

        rankings.replaceAll(existingRanking ->
                existingRanking.getUser().equals(ranking.getUser()) ? ranking : existingRanking
        );

        if (!rankings.contains(ranking)) {
            rankings.add(ranking);
        }

        rankings.sort(Comparator.comparingInt(UserRanking::getPoints).reversed());

        rankings.forEach(rank -> rank.setPosition(rankings.indexOf(rank) + 1));
    }


    @Override
    public List<User> getTopUsers(int n) {
        List<User> topUsers = new ArrayList<>();
        for (int i = 0; i < Math.min(n, rankings.size()); i++) {
            UserRanking ranking = rankings.get(i);

            topUsers.add(ranking.getUser());
        }
        return topUsers;
    }


    @Override
    public List<User> getUsersInRange(int minScore, int maxScore) {
        List<User> usersInRange = new ArrayList<>();
        for (UserRanking ranking : rankings) {
            if (ranking.getRanking(UserRanking.RankingType.POINTS) >= minScore && ranking.getRanking(UserRanking.RankingType.POINTS) <= maxScore) {
                usersInRange.add(ranking.getUser());
            }
        }
        return usersInRange;
    }

    @Override
    public int getPosition(User user) {
        return this.rankings.indexOf(user.getUserRanking()) + 1;
    }

    @Override
    public User getUserByIndex(int index) {
        if (index - 1 < this.rankings.size()) {
            return (this.rankings.get(index - 1)).getUser();
        }
        return null;
    }


}
