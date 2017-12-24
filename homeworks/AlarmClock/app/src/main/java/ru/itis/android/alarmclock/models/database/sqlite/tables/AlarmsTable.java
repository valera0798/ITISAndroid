package ru.itis.android.alarmclock.models.database.sqlite.tables;

/**
 * Created by Users on 06.11.2017.
 */

public class AlarmsTable {
    public static final String TABLE_NAME = "alarms";

    public static final String COLUMN_PRIMARY_KEY_ID = "_id";
    public static final String COLUMN_HOUR = "hour";
    public static final String COLUMN_MINUTE = "minute";
    public static final String COLUMN_DAYS = "days";
    public static final String COLUMN_IS_ACTIVE = "isActive";
    public static final String[] ATTRIBUTES =
            {COLUMN_PRIMARY_KEY_ID, COLUMN_HOUR, COLUMN_MINUTE, COLUMN_DAYS, COLUMN_IS_ACTIVE};

    public static String getCreateTableQuery() {
        return "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_PRIMARY_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_HOUR + " INTEGER NOT NULL, "
                + COLUMN_MINUTE + " INTEGER NOT NULL, "
                + COLUMN_DAYS + " TEXT NOT NULL, "
                + COLUMN_IS_ACTIVE + " INTEGER NOT NULL"
                + ");";
    }

    public static String getDeleteTableQuery() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
    }

    public static String getSelectAllQuery() {
        return "SELECT * FROM " + TABLE_NAME + ";";
    }

    public static String getSelectByIdQuery() {
        return "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PRIMARY_KEY_ID + "=? ;";
    }

    public static String getNextId() {
        return "SELECT MAX(" + COLUMN_PRIMARY_KEY_ID + ") FROM " + TABLE_NAME + ";";
    }
}
