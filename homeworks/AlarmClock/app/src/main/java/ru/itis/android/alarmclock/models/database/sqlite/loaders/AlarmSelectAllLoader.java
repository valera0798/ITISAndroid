package ru.itis.android.alarmclock.models.database.sqlite.loaders;

import android.content.Context;
import android.database.Cursor;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.DBWorker;
import ru.itis.android.alarmclock.models.database.sqlite.SQLiteManager;

/**
 * Created by Users on 25.11.2017.
 */

public class AlarmSelectAllLoader extends SQLiteCursorLoader{
    private DBWorker<Alarm> dbWorker;

    public AlarmSelectAllLoader(Context context, DBWorker dbWorker) {
        super(context);
        this.dbWorker = dbWorker;
    }

    @Override
    protected Cursor loadCursor() {
        return ((SQLiteManager) dbWorker).selectCursor();
    }
}
