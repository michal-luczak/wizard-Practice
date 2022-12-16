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

        rankings.replaceAll(existingRanking -> {
            if (existingRanking != null) {
                return existingRanking.getUser().equals(ranking.getUser()) ? ranking : existingRanking;
            } else {
                return null;
            }
        });

        if (!rankings.contains(ranking)) {
            rankings.add(ranking);
        }

        rankings.sort(new AbstractRanking.RankingComparator());

        rankings.forEach(rank -> rank.setPosition(rankings.indexOf(rank) + 1));
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
    public List<User> getUsersInRange(int start, int end){
        throw new UnsupportedOperationException("Not supported yet");
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
