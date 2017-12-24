package ru.itis.android.alarmclock.models.database.sqlite.loaders;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.DBWorker;
import ru.itis.android.alarmclock.models.database.sqlite.SQLiteManager;

/**
 * Created by Users on 25.11.2017.
 */

public class AlarmSelectByIdLoader extends SQLiteCursorLoader{
    public static final String ALARM_SELECT_BY_ID = "ALARM_SELECT_BY_ID";

    private DBWorker<Alarm> dbWorker;
    private int id;

    public AlarmSelectByIdLoader(Context context, DBWorker dbWorker, Bundle args) {
        super(context);
        this.dbWorker = dbWorker;
        this.id = args.getInt(ALARM_SELECT_BY_ID);
    }

    @Override
    protected Cursor loadCursor() {
        return ((SQLiteManager) dbWorker).selectCursorById(id);
    }
}
