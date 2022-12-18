package me.taison.wizardpractice.data.storage.database;

import me.taison.wizardpractice.data.storage.util.Database;
import me.taison.wizardpractice.data.user.User;

public interface SQLiteStorage extends Database {

    void loadUsers();

    void saveUsers();

    void saveUser(User user);

}
