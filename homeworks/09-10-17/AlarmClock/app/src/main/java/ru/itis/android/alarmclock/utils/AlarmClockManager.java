package ru.itis.android.alarmclock.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.receiver.AlarmReceiver;

/**
 * Created by Users on 26.10.2017.
 */

public class AlarmClockManager {
    public static final String ACTION_CREATE_NEW_ALARM = "ACTION_CREATE_NEW_ALARM";
    public static final String EXTRA_CURRENT_ALARM = "EXTRA_CURRENT_ALARM";
    private static final String ALARM_SET_TAG = "ALARM_SET_TAG";

    private static AlarmClockManager alarmClockManager;
    private AlarmManager alarmManager;
    private Context appContext;

    public static AlarmClockManager getInstance(Context appContext) {
        if (alarmClockManager == null)
            alarmClockManager = new AlarmClockManager(appContext);
        return alarmClockManager;
    }

    private AlarmClockManager(Context appContext) {
        this.appContext = appContext;
        alarmManager = getAlarmManager();
    }

    private AlarmManager getAlarmManager() {
        if (alarmManager == null)
            alarmManager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
        return alarmManager;
    }

//------------------------------------------------------------------------------------------------//
    public Intent createAlarmIntent(Context fromContext, Class toClass, Alarm currentAlarm) {
        Intent alarmIntent = new Intent(fromContext, toClass);
        alarmIntent.setAction(ACTION_CREATE_NEW_ALARM);
        alarmIntent.putExtra(EXTRA_CURRENT_ALARM, currentAlarm);

        return alarmIntent;
    }

    public PendingIntent createAlarmPendingIntent(Context fromContext, int id, Intent alarmIntent) {
        return PendingIntent
                .getBroadcast(fromContext,
                        id,
                        alarmIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // одноразовый запуск
    public void setAlarmOnTime(long intTime, PendingIntent alarmPendingIntent) {
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,  // вывести устройство из сна
                    intTime,  // через время
                    alarmPendingIntent);
        }
    }

    // интервальный запуск
    public void setAlarmOnTime(long nextAlarmTimeMillis, long interval, PendingIntent alarmPendingIntent) {
        alarmManager = getAlarmManager();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  // вывести устройство из сна
                nextAlarmTimeMillis,  // через время
                interval,   // повторять каждые
                alarmPendingIntent);
        Log.d(ALARM_SET_TAG, "alarm set. It will start in " + nextAlarmTimeMillis);
    }

    // перенаправление намерения
    public PendingIntent getRedirectedPendingIntent(Context receiverContext, Class redirectToClass, Intent externalIntent) {
        // у интента, вызванного AlarmManager-ом изменить класс получатель, сохранив все данные, которые находятся в нём
        externalIntent.setClass(receiverContext, redirectToClass);
        // интент на отмену уведомления
        return PendingIntent.getBroadcast(receiverContext, 1000, externalIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // удаление всех отложенных намерений
    public void deleteAllPendingIntent(List<Alarm> alarms) {
        Intent existedAlarmIntent;
        PendingIntent existedAlarmPendingIntent;

        for (int i = 0; i < alarms.size(); i++) {
            existedAlarmIntent =
                    alarmClockManager.createAlarmIntent(appContext, AlarmReceiver.class, alarms.get(i));
            existedAlarmPendingIntent =
                    alarmClockManager.createAlarmPendingIntent(appContext, alarms.get(i).getId(), existedAlarmIntent);
            alarmClockManager.cancelAlarm(existedAlarmPendingIntent);
        }
    }
    private void cancelAlarm(PendingIntent existedAlarmPendingIntent) {
        alarmManager = getAlarmManager();
        alarmManager.cancel(existedAlarmPendingIntent);
    }
}
