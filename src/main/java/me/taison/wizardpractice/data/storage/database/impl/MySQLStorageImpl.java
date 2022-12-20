package me.taison.wizardpractice.data.storage.database.impl;

import com.zaxxer.hikari.HikariDataSource;
import me.taison.wizardpractice.WizardPractice;
import me.taison.wizardpractice.data.storage.AbstractDatabase;
import me.taison.wizardpractice.data.storage.database.MySQLStorage;
import me.taison.wizardpractice.data.storage.util.Queries;
import me.taison.wizardpractice.data.user.User;
import me.taison.wizardpractice.data.user.impl.UserImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static me.taison.wizardpractice.data.storage.util.Queries.*;

public class MySQLStorageImpl extends AbstractDatabase implements MySQLStorage {

    private final WizardPractice wizardPractice;

    private HikariDataSource dataSource;

    public MySQLStorageImpl(WizardPractice wizardPractice) {
        super(wizardPractice);

        this.wizardPractice = wizardPractice;
    }

    @Override
    protected void create() {
        //TODO config later... nie ma czasu kurwaaaaa

        String databaseHost = "lastcraft.pl";
        String databasePort = "3306";
        String databaseUser = "root";
        String databaseName = "lastcraft";
        String databasePassword = "tajson_i_luxdevpl_wizard_practice_haslo_xd";

        this.dataSource = new HikariDataSource();

        this.dataSource.setMaximumPoolSize(6);

        this.dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.dataSource.setJdbcUrl("jdbc:mysql://" + databaseHost + ":" + databasePort + "/" + databaseName);

        this.dataSource.setUsername(databaseUser);
        this.dataSource.setPassword(databasePassword);

        this.dataSource.addDataSourceProperty("cachePrepStmts", true);
        this.dataSource.addDataSourceProperty("prepStmtCacheSize", 250);
        this.dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        this.dataSource.addDataSourceProperty("useServerPrepStmts", true);
        this.dataSource.addDataSourceProperty("serverTimezone", "Europe/Warsaw");

        this.initTables();

        this.loadAll();

        wizardPractice.getLogger().info("Connection with database has been initialized.");
    }

    @Override
    protected void destroy() {
        wizardPractice.getUserFactory().findAll().forEach(this::saveUser);

        this.dataSource.close();

        wizardPractice.getLogger().info("Connection with database has been closed.");
    }

    private void initTables() {
        try (Connection connection = this.dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(USER_TABLES)) {
                preparedStatement.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public void loadAll() {
        try (Connection connection = this.dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USERS)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    User user = new UserImpl(UUID.fromString(resultSet.getString("uuid")), resultSet.getString("nickname"));
                    user.fromResultSet(resultSet);

                    wizardPractice.getUserFactory().registerUser(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(User user) {
        String tableName = user.getTableName();

        String[] columnNames = user.getColumnNames();

        Object[] columnValues = user.getColumnValues();

        try (Connection connection = this.dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(Queries.getInsertQuery(tableName, columnNames))) {
            for (int i = 0; i < columnValues.length; i++) {
                preparedStatement.setObject(i + 1, columnValues[i]);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
