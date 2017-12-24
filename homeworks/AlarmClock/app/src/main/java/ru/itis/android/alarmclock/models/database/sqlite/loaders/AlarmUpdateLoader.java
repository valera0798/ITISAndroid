package ru.itis.android.alarmclock.models.database.sqlite.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.DBWorker;

/**
 * Created by Users on 25.11.2017.
 */

public class AlarmUpdateLoader extends AsyncTaskLoader{
    public static final String ALARM_UPDATE_OLD = "ALARM_UPDATE_OLD";
    public static final String ALARM_UPDATE_NEW = "ALARM_UPDATE_NEW";

    private DBWorker<Alarm> dbWorker;
    private Alarm oldAlarm;
    private Alarm newAlarm;

    public AlarmUpdateLoader(Context context, DBWorker dbWorker, Bundle args) {
        super(context);
        this.dbWorker = dbWorker;
        this.oldAlarm = args.getParcelable(ALARM_UPDATE_OLD);
        this.newAlarm = args.getParcelable(ALARM_UPDATE_NEW);
    }

    @Override
    public Object loadInBackground() {
        dbWorker.update(oldAlarm, newAlarm);
        return null;
    }
}
