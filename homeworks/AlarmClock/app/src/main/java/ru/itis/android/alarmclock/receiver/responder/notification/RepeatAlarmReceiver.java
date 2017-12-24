package ru.itis.android.alarmclock.receiver.responder.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import ru.itis.android.alarmclock.receiver.AlarmReceiver;
import ru.itis.android.alarmclock.utils.AlarmClockManager;

public class RepeatAlarmReceiver extends NotificationReceiver {
    public static final String LOG_D_TAG_REPEAT_ALARM_RECEIVER = "REPEAT_ALARM_RECEIVER";

    private static final long inTime = 1000 * 5; // 5 секунд

    private AlarmClockManager alarmClockManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(AlarmClockManager.ACTION_CREATE_NEW_ALARM) &&
                intent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM) != null) {
            alarmClockManager = AlarmClockManager.getInstance(context);
            PendingIntent alarmRepeatPendingIntent =
                    alarmClockManager.getRedirectedPendingIntent(context, AlarmReceiver.class, intent);
            alarmClockManager.setAlarmOnTime(System.currentTimeMillis() + inTime, alarmRepeatPendingIntent);

            cancel(context, intent);
        }
    }
}
