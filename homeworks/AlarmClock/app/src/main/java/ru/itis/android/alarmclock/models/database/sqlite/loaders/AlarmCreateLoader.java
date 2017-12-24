package ru.itis.android.alarmclock.models.database.sqlite.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.DBWorker;

/**
 * Created by Users on 25.11.2017.
 */

public class AlarmCreateLoader extends AsyncTaskLoader {
    public static final String ALARM_CREATE = "ALARM_CREATE";

    private DBWorker<Alarm> dbWorker;
    private Alarm alarm;

    public AlarmCreateLoader(Context context, DBWorker dbWorker, Bundle args) {
        super(context);
        this.dbWorker = dbWorker;
        this.alarm = args.getParcelable(ALARM_CREATE);
    }

    @Override
    public Object loadInBackground() {
        dbWorker.putEntity(alarm);
        return null;
    }
}
