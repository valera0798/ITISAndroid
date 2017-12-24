package ru.itis.android.alarmclock.models.database.sqlite.loaders;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.DBWorker;

/**
 * Created by Users on 25.11.2017.
 */

public class AlarmDeleteAllLoader extends AsyncTaskLoader {
    public static final String ALARM_DELETE_ALL = "ALARM_DELETE_ALL";

    private DBWorker<Alarm> dbWorker;

    public AlarmDeleteAllLoader(Context context, DBWorker dbWorker, Bundle args) {
        super(context);
        this.dbWorker = dbWorker;
    }

    @Override
    public Object loadInBackground() {
        dbWorker.deleteAll();
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();    // запуск асинронного действия. игнорирует предыдущие данные, просто загружает новые
                        // вызывает метод onForceLoad()
                        // должен вызываться из main потока
    }
}
