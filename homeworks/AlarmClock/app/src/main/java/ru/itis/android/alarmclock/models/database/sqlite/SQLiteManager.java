package ru.itis.android.alarmclock.models.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.DBWorker;
import ru.itis.android.alarmclock.models.database.sqlite.tables.AlarmsTable;

/**
 * Created by Users on 06.11.2017.
 *
 * CRUD - через методы SQLiteDatabase (ContentValues)
 *
 */

public class SQLiteManager implements DBWorker<Alarm> {
    private static SQLiteManager sqLiteManager;

    private Context appContext;
    private SQLiteOpenHelper openHelper;

    public static SQLiteManager getInstance(Context appContext) {
        if (sqLiteManager == null) {
            sqLiteManager = new SQLiteManager(appContext);
        }
        return sqLiteManager;
    }
    private SQLiteManager(Context appContext) {
        this.appContext = appContext;
        openHelper = new AlarmClockDBHelper(appContext);
    }

    public SQLiteOpenHelper getOpenHelper() {
        if (openHelper == null) {
            openHelper = new AlarmClockDBHelper(appContext);
        }
        return openHelper;
    }

//-------------------------------Реализация необходимых методов БД--------------------------------//
    @Override
    public List<Alarm> getEntities() {
        SQLiteDatabase database = getReadableDatabase();

        Cursor selectedItems = database.query(
                AlarmsTable.TABLE_NAME,
                AlarmsTable.ATTRIBUTES,
                null, null, null, null,
                AlarmsTable.COLUMN_PRIMARY_KEY_ID + " DESC");
        List<Alarm> alarms = new AlarmWrapper(selectedItems).getAlarms();
        selectedItems.close();

        return alarms;
    }

    @Override
    public Alarm getElementById(int id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor selectedItem = database.query(
                AlarmsTable.TABLE_NAME,
                AlarmsTable.ATTRIBUTES,
                AlarmsTable.COLUMN_PRIMARY_KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null, null, null);
        Alarm alarm = new AlarmWrapper(selectedItem).getAlarm(0);
        selectedItem.close();

        return alarm;
    }

    @Override
    public void putEntity(Alarm alarm) {
        SQLiteDatabase database = getWritableDatabase();

        database.insert(AlarmsTable.TABLE_NAME, null, getContentValues(alarm));
    }

    private ContentValues getContentValues(Alarm alarm) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(AlarmsTable.COLUMN_PRIMARY_KEY_ID, alarm.getId());
        contentValues.put(AlarmsTable.COLUMN_HOUR, alarm.getHour());
        contentValues.put(AlarmsTable.COLUMN_MINUTE, alarm.getMinute());
        contentValues.put(AlarmsTable.COLUMN_DAYS, AlarmWrapper.getDaysToString(alarm.getDays()));
        contentValues.put(AlarmsTable.COLUMN_IS_ACTIVE, String.valueOf(AlarmWrapper.convertBooleanToChar(alarm.isActive())));

        return contentValues;
    }

    @Override
    public void deleteAll() {
        SQLiteDatabase database = getWritableDatabase();

        database.delete(AlarmsTable.TABLE_NAME, null, null);
    }

    public Cursor selectCursor() {
        return getReadableDatabase().rawQuery(AlarmsTable.getSelectAllQuery(), null);
    }

    public Cursor selectCursorById(int id) {
        return getReadableDatabase().rawQuery(AlarmsTable.getSelectByIdQuery(), new String[] { String.valueOf(id) });
    }

    @Override
    public void update(Alarm currentAlarm, Alarm updatedAlarm) {
        SQLiteDatabase database = getWritableDatabase();

        database.update(
                AlarmsTable.TABLE_NAME,
                getContentValues(updatedAlarm),
                AlarmsTable.COLUMN_PRIMARY_KEY_ID + "=?",
                new String[] {String.valueOf(currentAlarm.getId())} );
    }

    @Override
    public int getNewId() {
        SQLiteDatabase database = getReadableDatabase();

        Cursor selectedItems = database.rawQuery(AlarmsTable.getNextId(), null);
        int id = 0;
        if (selectedItems.moveToFirst()) {
            do {
                id = selectedItems.getInt(0);
            } while(selectedItems.moveToNext());
        }
        selectedItems.close();

        return ++id;
    }

//-----------------------------Специфика реализации БД ч/з SQLite---------------------------------//
    private SQLiteDatabase getWritableDatabase() {
        return getOpenHelper().getWritableDatabase();
    }
    private SQLiteDatabase getReadableDatabase() {
        return getOpenHelper().getReadableDatabase();
    }
}
