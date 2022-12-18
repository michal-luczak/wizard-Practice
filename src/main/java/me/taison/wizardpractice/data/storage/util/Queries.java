package me.taison.wizardpractice.data.storage.util;

public final class Queries {

    public static String SELECT_USERS = "SELECT * FROM `users`";

    public static String USER_TABLES = "CREATE TABLE IF NOT EXISTS `users` (" +
            "`uuid` varchar(100) not null, " +
            "`nickname` varchar(30) not null, " +
            "`points` int not null, " +
            "`kills` int not null, " +
            "`deaths` int not null, " +
            "primary key (uuid)" +
            ");";

    public static String USER_INSERT_QUERY = "INSERT INTO `users` (" +
            "`uuid`, `nickname`, `points`, `kills`, `deaths`) " +
            "VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE " +
            "`uuid` = VALUES(`uuid`), `nickname` = VALUES(`nickname`), `points` = VALUES(`points`), `kills` = VALUES(`kills`), `deaths` = VALUES(`deaths`);";


}
