package me.taison.wizardpractice.data.factory;

import me.taison.wizardpractice.data.user.User;

import java.util.Optional;
import java.util.UUID;

public interface UserFactory {

    Optional<User> getByUniqueId(UUID uniqueIdentifier);

    Optional<User> getUserByName(String name);

    void registerUser(User user);

    void unregisterUser(User boxUser);

    void saveBoxUsers();

    void loadBoxUsers();

}
