package ru.itis.android.alarmclock.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.utils.AlarmClockManager;

public class RepeatAlarmReceiver extends BroadcastReceiver {
    public static final String TAG_REPEAT_ALARM_RECEIVER = "REPEAT_ALARM_RECEIVER";
    private static final long inTime = 1000 * 10; // 10 секунд

    private AlarmClockManager alarmClockManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG_REPEAT_ALARM_RECEIVER, "dismiss action");
        alarmClockManager = AlarmClockManager.getInstance(context);
        PendingIntent alarmRepeatPendingIntent =
                alarmClockManager.getRedirectedPendingIntent(context, AlarmReceiver.class, intent);
        alarmClockManager.setAlarmOnTime(System.currentTimeMillis() + inTime, alarmRepeatPendingIntent);

        // TODO NotificationManager
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Alarm currentAlarm = (Alarm) intent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM);
        notificationManager.cancel(currentAlarm.getId());
    }
}
