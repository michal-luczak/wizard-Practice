package me.taison.wizardpractice.data;

import java.util.UUID;

public interface IDatabaseUser {

    UUID getUniqueIdentifier();

    String getName();

    int getRanking();

}
