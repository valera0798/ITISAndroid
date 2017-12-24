package ru.itis.android.alarmclock.receiver.responder.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.utils.AlarmClockManager;
import ru.itis.android.alarmclock.utils.NotificationManager;

/**
 * Created by Users on 30.10.2017.
 */

public abstract class NotificationReceiver extends BroadcastReceiver{
    private NotificationManager notificationManager;

    @Override
    public abstract void onReceive(Context context, Intent intent);

    protected void cancel(Context context, Intent intent) {
        notificationManager = NotificationManager.getInstance(context);

        Alarm currentAlarm = (Alarm) intent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM);
        notificationManager.cancel(currentAlarm.getId());
    }
}
