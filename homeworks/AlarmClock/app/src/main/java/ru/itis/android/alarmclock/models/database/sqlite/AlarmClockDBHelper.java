package ru.itis.android.alarmclock.models.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.itis.android.alarmclock.models.database.sqlite.tables.AlarmsTable;

/**
 * Created by Users on 06.11.2017.
 */

public class AlarmClockDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "alarmClock.db";
    public static final int DB_VERSION = 1;

    public AlarmClockDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(AlarmsTable.getCreateTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(AlarmsTable.getDeleteTableQuery());
        onCreate(sqLiteDatabase);
    }
}
