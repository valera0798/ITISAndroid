package ru.itis.android.alarmclock.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ru.itis.android.alarmclock.fragments.start.AlarmListFragment;
import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.utils.AlarmClockManager;
import ru.itis.android.alarmclock.utils.MusicPlayerManager;

public class CancelAlarmReceiver extends BroadcastReceiver {
    public static final String TAG_CANCEL_ALARM_RECEIVER = "CANCEL_ALARM_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG_CANCEL_ALARM_RECEIVER, "dismiss action");
        // TODO NotificationManager
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Alarm currentAlarm = (Alarm) intent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM);
        notificationManager.cancel(currentAlarm.getId());
    }
}
