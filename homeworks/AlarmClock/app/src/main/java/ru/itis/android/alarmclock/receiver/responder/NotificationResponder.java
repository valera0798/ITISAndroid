package ru.itis.android.alarmclock.receiver.responder;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;

import ru.itis.android.alarmclock.R;
import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.utils.AlarmClockManager;
import ru.itis.android.alarmclock.utils.MusicPlayerManager;
import ru.itis.android.alarmclock.utils.NotificationManager;

/**
 * Created by Users on 30.10.2017.
 */

public class NotificationResponder extends Responder {
    private static NotificationResponder notificationResponder;

    private Context context;
    private Intent intent;

    private MusicPlayerManager musicPlayerManager;
    private AlarmClockManager alarmClockManager;
    private NotificationManager notificationManager;

    public static Responder getInstance(Context context, Intent intent) {
        if (notificationResponder == null)
            notificationResponder = new NotificationResponder(context, intent);
        return notificationResponder;
    }
    private NotificationResponder(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;

        alarmClockManager = AlarmClockManager.getInstance(context);
        musicPlayerManager = MusicPlayerManager.getInstance(context);
        notificationManager = NotificationManager.getInstance(context);

        notificationManager.initNotificationParams(R.drawable.ic_alarm,
                context.getString(R.string.notification_alarm_ticker),
                context.getString(R.string.notification_content_title),
                context.getString(R.string.notification_content_text),

                R.drawable.ic_dismiss,
                context.getString(R.string.notification_positive_action_text),

                R.drawable.ic_snooze,
                context.getString(R.string.notification_negative_action_text));

        notificationManager.configureAlarmNotificationBuilder(Notification.CATEGORY_ALARM); // высокий приоритет уведомления
        notificationManager.configureAlarmNotificationActions(alarmClockManager, context, intent);

        createAlarmNotification();
    }

    private Notification createAlarmNotification() {
        Notification alarmNotification = notificationManager.buildNotification();

        musicPlayerManager.setMusic(alarmNotification, musicPlayerManager.getMusic(MusicPlayerManager.TYPE_DEFAULT));
        notificationManager.setFlags(alarmNotification);

        return alarmNotification;
    }

    @Override
    public void response() {
        Alarm currentAlarm = (Alarm) intent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM);
        notificationManager.notify(currentAlarm.getId(), createAlarmNotification());
    }
}
