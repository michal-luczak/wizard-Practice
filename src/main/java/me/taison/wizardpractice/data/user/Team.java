package me.taison.wizardpractice.data.user;

import com.google.common.cache.Cache;

import java.util.List;

public interface Team {

    List<User> getTeam();

    Cache<User, String> getInvitations();

    User getLeader();

    void setLeader(User leader);

    void invitePlayer(User user);

    void kickPlayer(User user);

    void disband();

    void sendTitle(String title, String subtitle, int fadeIn, int fadeOut, int time);

    void sendActionBar(String message);

    void leave(User user);

    void sendMessage(String message);

    void join(User user);

}
