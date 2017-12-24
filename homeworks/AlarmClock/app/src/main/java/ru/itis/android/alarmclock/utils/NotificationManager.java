package ru.itis.android.alarmclock.utils;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import ru.itis.android.alarmclock.receiver.responder.notification.CancelAlarmReceiver;
import ru.itis.android.alarmclock.receiver.responder.notification.RepeatAlarmReceiver;

/**
 * Created by Users on 30.10.2017.
 */

public class NotificationManager {
    private static NotificationManager notificationManagerAlarmClock;

    private int notificationSmallIcon;
    private String notificationTicker;
    private String notificationContentTitle;
    private String notificationContentText;
    private int dismissActionIcon;
    private int snoozeActionIcon;
    private String dismissActionText;
    private String snoozeActionText;

    private Context appContext;
    private NotificationCompat.Builder builder;
    private android.app.NotificationManager notificationManager;

    public static NotificationManager getInstance(Context appContext) {
        if (notificationManagerAlarmClock == null)
            notificationManagerAlarmClock = new NotificationManager(appContext);
        return notificationManagerAlarmClock;
    }

    private NotificationManager(Context appContext) {
        this.appContext = appContext;
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        if (builder == null)
            builder = new NotificationCompat.Builder(appContext);
        return builder;
    }

    private android.app.NotificationManager getNotificationManager() {
        if (notificationManager == null)
            notificationManager = (android.app.NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
        return notificationManager;
    }

//---------------------------------Основные методы------------------------------------------------//
    public void configureAlarmNotificationBuilder(String alarmCategory) {
        builder = getNotificationBuilder();
        builder
                .setPriority(Notification.PRIORITY_HIGH)    // Head-up уведомление
                .setDefaults(Notification.DEFAULT_VIBRATE)

                .setSmallIcon(notificationSmallIcon)
                .setTicker(notificationTicker)  // заголовок в строке состояния
                .setContentTitle(notificationContentTitle)  // заголовок уведомления
                //.setContentText(notificationContentText)  // текст уведомления

                .setAutoCancel(false);   // отключить автоматическое исчезание
                //.setCategory(alarmCategory);
    }

    public void configureAlarmNotificationActions(AlarmClockManager alarmClockManager, Context context, Intent intent) {
        // отключить будильник
        NotificationCompat.Action actionDismiss = new NotificationCompat.Action.Builder(
                dismissActionIcon,
                dismissActionText,
                alarmClockManager.getRedirectedPendingIntent(context, CancelAlarmReceiver.class, intent)
        ).build();
        builder.addAction(actionDismiss);   // намерения на отключение будильника

        // запустить уведомление через 1 минуту
        NotificationCompat.Action actionSnooze = new NotificationCompat.Action.Builder(
                snoozeActionIcon,
                snoozeActionText,
                alarmClockManager.getRedirectedPendingIntent(context, RepeatAlarmReceiver.class, intent)
        ).build();
        builder.addAction(actionSnooze);   // намерения на повтор будильника
    }

    public Notification buildNotification() {
        return builder.build();
    }

    public void setFlags(Notification notification) {
        notification.flags = Notification.FLAG_AUTO_CANCEL;
    }

    public void notify(int id, Notification alarmNotification) {
        notificationManager = getNotificationManager();
        notificationManager.notify(id, alarmNotification);
    }

    public void cancel(int id) {
        notificationManager = getNotificationManager();
        notificationManager.cancel(id);
    }

//---------------------------------Вспомогательные методы-----------------------------------------//
    public void initNotificationParams(int smallIconId, String ticker, String contentTitle, String contentText,
                                       int positiveActionIconId, String positiveActionText,
                                       int negativeActionIconId, String negativeActionText) {
        notificationSmallIcon = smallIconId;
        notificationTicker = ticker;
        notificationContentTitle = contentTitle;
        notificationContentText = contentText;

        dismissActionIcon = positiveActionIconId;
        dismissActionText = positiveActionText;

        snoozeActionIcon = negativeActionIconId;
        snoozeActionText = negativeActionText;
    }
}
