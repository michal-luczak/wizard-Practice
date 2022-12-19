package me.taison.wizardpractice.data.storage.util;

import java.util.Arrays;
import java.util.Collections;

public final class Queries {

    //TODO TESTING...
    public static String getInsertQuery(String tableName, String[] columnNames) {
        String query = "INSERT INTO " + tableName + " (" + String.join(", ", columnNames) + ") VALUES (" + String.join(", ", Collections.nCopies(columnNames.length, "?")) + ")";
        String update = String.join(", ", Arrays.stream(columnNames).map(column -> column + " = VALUES("+ column +")").toArray(String[]::new));
        return query + " ON DUPLICATE KEY UPDATE " + update;
    }

    public static String SELECT_USERS = "SELECT * FROM `users`";

    public static String USER_TABLES = "CREATE TABLE IF NOT EXISTS `users` (" +
            "`uuid` varchar(100) not null, " +
            "`nickname` varchar(30) not null, " +
            "`points` int not null, " +
            "`kills` int not null, " +
            "`deaths` int not null, " +
            "primary key (uuid)" +
            ");";

    public static String USER_INSERT_QUERY =
            "INSERT INTO `users` (" +
            "`uuid`, `nickname`, `points`, `kills`, `deaths`) " +
            "VALUES (?, ?, ?, ?, ?)" +
            " ON DUPLICATE KEY UPDATE " +
            "`uuid` = VALUES(`uuid`)," +
            " `nickname` = VALUES(`nickname`)," +
            " `points` = VALUES(`points`)," +
            " `kills` = VALUES(`kills`)," +
            " `deaths` = VALUES(`deaths`);";


}
