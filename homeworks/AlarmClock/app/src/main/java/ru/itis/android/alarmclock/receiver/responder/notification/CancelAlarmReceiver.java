package ru.itis.android.alarmclock.receiver.responder.notification;

import android.content.Context;
import android.content.Intent;

import ru.itis.android.alarmclock.utils.AlarmClockManager;

public class CancelAlarmReceiver extends NotificationReceiver {
    public static final String LOG_D_TAG_CANCEL_ALARM_RECEIVER = "CANCEL_ALARM_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(AlarmClockManager.ACTION_CREATE_NEW_ALARM) &&
                intent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM) != null) {
            cancel(context, intent);
        }
    }
}
