package me.taison.wizardpractice.data.user.impl.ranking;

import me.taison.wizardpractice.data.user.User;

import java.util.Comparator;

public abstract class AbstractRanking<T extends Comparable<T>> {

    private final User user;
    private int position;

    private T ranking;

    public AbstractRanking(User user) {
        this.user = user;
    }

    public abstract T getRanking();

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public static class RankingComparator implements Comparator<AbstractRanking<?>> {

        @Override
        public int compare(AbstractRanking<?> o1, AbstractRanking<?> o2) {
            int pointsDifference = ((Comparable) o2.getRanking()).compareTo(o1.getRanking());

            if (pointsDifference != 0) {
                return pointsDifference;
            }

            return o1.getUser().getUniqueIdentifier().toString().compareTo(o2.getUser().getUniqueIdentifier().toString());
        }

    }
}
