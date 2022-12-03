package me.taison.wizardpractice.data.factory;

import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.user.User;

import java.util.Map;
import java.util.Optional;

public interface TeamFactory {

    Map<User, Team> getTeams();

    default Optional<Team> getTeamByUser(User user){
        return Optional.ofNullable(this.getTeams().get(user));
    }

    void register(Team team);

    void unregister(Team team);

}
