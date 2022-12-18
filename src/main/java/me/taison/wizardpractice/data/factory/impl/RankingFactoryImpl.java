package me.taison.wizardpractice.data.factory.impl;

import me.taison.wizardpractice.data.factory.RankingFactory;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.ranking.AbstractRanking;
import me.taison.wizardpractice.data.user.impl.ranking.RankingType;

import java.util.*;
import java.util.stream.Collectors;

public class RankingFactoryImpl implements RankingFactory {

    private final List<AbstractRanking<?>> rankings;

    public RankingFactoryImpl() {
        this.rankings = new ArrayList<>();

    }

    public void update(User user, RankingType rankingType) {
        AbstractRanking<?> ranking = user.getUserRanking(rankingType);

        if (ranking == null || !ranking.getType().equals(rankingType)) {
            return;
        }

        int index = rankings.indexOf(ranking);
        if (index == -1) {
            index = 0;
            while (index < rankings.size() && new AbstractRanking.RankingComparator().compare(ranking, rankings.get(index)) > 0) {
                index++;
            }
            rankings.add(index, ranking);
        } else {
            rankings.set(index, ranking);
        }

        this.updatePositions();
    }

    private void updatePositions() {
        rankings.sort(new AbstractRanking.RankingComparator());
        rankings.forEach(rank -> rank.setPosition(rankings.indexOf(rank) + 1));
    }

    @Override
    public void addRanking(AbstractRanking<?> abstractRanking) {
        this.rankings.add(abstractRanking);

        this.update(abstractRanking.getUser(), abstractRanking.getType());
    }

    @Override
    public List<User> getTopUsers(int n, RankingType rankingType) {
        List<AbstractRanking<?>> filteredRankings = rankings.stream()
                .filter(rankingType::isMatchingType).toList();

        return filteredRankings.stream()
                .limit(n)
                .map(AbstractRanking::getUser)
                .collect(Collectors.toList());
    }



    @Override
    public List<User> getUsersInRange(RankingType rankingType, int start, int end) {
        List<AbstractRanking<?>> filteredRankings = rankings.stream()
                .filter(rankingType::isMatchingType).toList();

        int firstIndex = -1;
        int lastIndex = -1;
        for (int i = 0; i < filteredRankings.size(); i++) {
            if ((Integer) filteredRankings.get(i).getRanking() >= start && firstIndex == -1) {
                firstIndex = i;
            }
            if ((Integer) filteredRankings.get(i).getRanking() <= end) {
                lastIndex = i;
            }
        }

        if (firstIndex != -1 && lastIndex != -1) {
            List<User> usersInRange = new ArrayList<>();
            for (int i = firstIndex; i <= lastIndex; i++) {
                usersInRange.add(filteredRankings.get(i).getUser());
            }
            return usersInRange;
        }
        return Collections.emptyList();
    }

    @Override
    public int getPosition(User user, RankingType rankingType) {
        return this.rankings.indexOf(user.getUserRanking(rankingType)) + 1;
    }

    @Override
    public User getUserByIndex(int index) {
        if (index - 1 < this.rankings.size()) {
            return (this.rankings.get(index - 1)).getUser();
        }
        return null;
    }


}
