package ru.itis.android.alarmclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.DBWorker;
import ru.itis.android.alarmclock.models.database.shared_preferences.SharedPreferencesManager;
import ru.itis.android.alarmclock.models.database.sqlite.SQLiteManager;
import ru.itis.android.alarmclock.utils.AlarmClockManager;

/**
 * Created by Users on 30.10.2017.
 */

public class SystemRebootReceiver extends BroadcastReceiver {
    private DBWorker<Alarm> dbWorker;
    private AlarmClockManager alarmClockManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        android.os.Debug.waitForDebugger();
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) &&
                intent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM) != null) {
            //dbWorker = SharedPreferencesManager.getInstance(context);
            dbWorker = SQLiteManager.getInstance(context);
            List<Alarm> alarms = dbWorker.getEntities();

            alarmClockManager = AlarmClockManager.getInstance(context);
            alarmClockManager.startAlarms(alarms, AlarmReceiver.class);
        }
    }
}
