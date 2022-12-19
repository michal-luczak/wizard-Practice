package me.taison.wizardpractice.data.storage.database.persistable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface PersistableLoadable {

    String getTableName();

    String getPrimaryKeyColumnName();

    Object getPrimaryKey();

    void fromResultSet(ResultSet resultSet) throws SQLException;

}
