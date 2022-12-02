package me.taison.wizardpractice.data.impl;

import me.taison.wizardpractice.data.IDatabaseUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PracticeUser implements IDatabaseUser {

    private final UUID uniqueIdentifier;

    private final String name;

    private int ranking;

    public PracticeUser(UUID uniqueIdentifier, String name){
        this.uniqueIdentifier = uniqueIdentifier;
        this.ranking = ranking;
        this.name = name;
    }

    public PracticeUser(ResultSet resultSet) throws SQLException {
        this.uniqueIdentifier = UUID.fromString(resultSet.getString("uuid"));
        this.ranking = resultSet.getInt("ranking");
        this.name = resultSet.getString("name");
    }

    @Override
    public UUID getUniqueIdentifier() {
        return this.uniqueIdentifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getRanking() {
        return this.ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
}
