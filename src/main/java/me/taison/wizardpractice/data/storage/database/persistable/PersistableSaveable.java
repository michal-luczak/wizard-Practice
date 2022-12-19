package me.taison.wizardpractice.data.storage.database.persistable;

public interface PersistableSaveable {

    String getTableName();

    String[] getColumnNames();

    Object[] getColumnValues();

}
