package me.taison.wizardpractice.data.user.impl;

import me.taison.wizardpractice.data.user.User;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;

public class UserRanking implements Comparable<UserRanking> {

    public enum RankingType {
        DEFEATED_PLAYERS,
        POINTS,
        DEATHS
    }

    private final User user;

    private final Map<RankingType, Integer> rankings;

    private int position;

    public UserRanking(User user) {
        this.user = user;

        this.rankings = new TreeMap<>();

    }

    public void addKill(){
        rankings.put(RankingType.DEFEATED_PLAYERS, getRanking(RankingType.DEFEATED_PLAYERS) + 1);
    }

    public void addDeath(){
        rankings.put(RankingType.DEATHS, getRanking(RankingType.DEATHS) + 1);
    }

    public void addPoints(int points){
        rankings.put(RankingType.POINTS, getRanking(RankingType.POINTS) + points);
    }

    public void removePoints(int points){
        rankings.put(RankingType.POINTS, getRanking(RankingType.POINTS) - points);
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getRanking(RankingType rankingType) {
        Integer ranking = rankings.get(rankingType);
        return ranking == null ? 0 : ranking;
    }

    public int getPoints(){
        return this.getRanking(RankingType.POINTS);
    }

    @Override
    public int compareTo(@NotNull UserRanking otherRanking) {
        int pointsDifference = otherRanking.getRanking(RankingType.POINTS) - this.getRanking(RankingType.POINTS);

        if (pointsDifference != 0) {
            return pointsDifference;
        }

        String thisId = user.getUniqueIdentifier().toString();
        String otherId = otherRanking.getUser().getUniqueIdentifier().toString();

        if (thisId == null) {
            return otherId == null ? 0 : -1;
        }
        if (otherId == null) {
            return 1;
        }

        return thisId.compareTo(otherId);
    }

    public User getUser() {
        return user;
    }
}
