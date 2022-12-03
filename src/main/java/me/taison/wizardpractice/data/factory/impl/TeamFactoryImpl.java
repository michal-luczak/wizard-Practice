package me.taison.wizardpractice.data.factory.impl;

import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.user.Team;
import me.taison.wizardpractice.data.factory.TeamFactory;
import me.taison.wizardpractice.data.user.User;

import java.util.HashMap;
import java.util.Map;

public class TeamFactoryImpl implements TeamFactory {

    private final WizardPractice wizardPractice;
    private final Map<User, Team> teamMap;

    public TeamFactoryImpl(WizardPractice wizardPractice){
        this.wizardPractice = wizardPractice;

        this.teamMap = new HashMap<>();
    }

    @Override
    public Map<User, Team> getTeams() {
        return this.teamMap;
    }

    @Override
    public void register(Team team) {
        this.teamMap.put(team.getLeader(), team);
    }

    @Override
    public void unregister(Team team) {
        this.teamMap.remove(team.getLeader());
    }
}
