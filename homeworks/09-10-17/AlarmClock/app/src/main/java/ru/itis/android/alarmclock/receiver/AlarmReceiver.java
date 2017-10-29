package ru.itis.android.alarmclock.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import ru.itis.android.alarmclock.R;
import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.utils.AlarmClockManager;
import ru.itis.android.alarmclock.utils.MusicPlayerManager;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String ALARM_RECEIVER_TAG = "ALARM__RECEIVER_TAG";

    private NotificationCompat.Builder builder;
    private int notificationSmallIcon;
    private String notificationTicker;
    private String notificationContentTitle;
    private String currentTime;
    private String notificationContentText;
    private int dismissActionIcon;
    private int snoozeActionIcon;
    private String dismissActionText;
    private String snoozeActionText;

    private MusicPlayerManager musicPlayerManager;
    private AlarmClockManager alarmClockManager;
    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(ALARM_RECEIVER_TAG, intent.getAction());
        if (intent.getAction().equals(AlarmClockManager.ACTION_CREATE_NEW_ALARM)) { // если получен будильник, созданный в AlarmClock
            alarmClockManager = AlarmClockManager.getInstance(context);
            musicPlayerManager = MusicPlayerManager.getInstance(context);

            builder = new NotificationCompat.Builder(context);
            initNotificationParams(context);
            configureAlarmNotificationBuilder(builder, context, intent);

            Notification alarmNotification = builder.build();
            musicPlayerManager.configureAlarmSound(alarmNotification);
            alarmNotification.flags = Notification.FLAG_AUTO_CANCEL;

            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Alarm currentAlarm = (Alarm) intent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM);
            notificationManager.notify(currentAlarm.getId(), alarmNotification);
        }
    }

    private void initNotificationParams(Context context) {
        notificationSmallIcon = R.drawable.ic_alarm;
        notificationTicker = context.getString(R.string.notification_alarm_ticker);
        notificationContentTitle = context.getString(R.string.notification_content_title);
        notificationContentText = context.getString(R.string.notification_content_text);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");
        date.setTimeZone(calendar.getTimeZone());
        currentTime = date.format(currentLocalTime);

        dismissActionIcon = R.drawable.ic_dismiss;
        snoozeActionIcon = R.drawable.ic_snooze;
        dismissActionText = context.getString(R.string.notification_positive_action_text);
        snoozeActionText = context.getString(R.string.notification_negative_action_text);
    }

    private void configureAlarmNotificationBuilder(NotificationCompat.Builder builder, Context context, Intent intent) {
        builder
                .setPriority(Notification.PRIORITY_HIGH)    // Head-up уведомление
                .setDefaults(Notification.DEFAULT_VIBRATE)

                .setSmallIcon(notificationSmallIcon)
                .setTicker(notificationTicker)  // заголовок в строке состояния
                .setContentTitle(notificationContentTitle)  // заголовок уведомления
                .setContentText(notificationContentText + currentTime)  // текст уведомления

                .setAutoCancel(false)   // отключить автоматическое исчезание
                .setCategory(Notification.CATEGORY_ALARM);   // высокий приоритет уведомления

        // отключить будильник
        builder.addAction(dismissActionIcon, dismissActionText,
                alarmClockManager.getRedirectedPendingIntent(context, CancelAlarmReceiver.class, intent));   // намерения на отключение будильника

        // запустить уведомление через 1 минуту
        builder.addAction(snoozeActionIcon, snoozeActionText,
                alarmClockManager.getRedirectedPendingIntent(context, RepeatAlarmReceiver.class, intent));   // намерения на повтор будильника
    }
}
