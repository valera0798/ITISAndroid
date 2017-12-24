package ru.itis.android.alarmclock.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.NextAlarmTime;
import ru.itis.android.alarmclock.receiver.AlarmReceiver;

/**
 * Created by Users on 26.10.2017.
 */

public class AlarmClockManager {
    private static final String LOG_D_ALARM_SET_TAG = "ALARM_SET_TAG";

    public static final String ACTION_CREATE_NEW_ALARM = "ACTION_CREATE_NEW_ALARM";
    public static final String EXTRA_CURRENT_ALARM = "EXTRA_CURRENT_ALARM";

    private static AlarmClockManager alarmClockManager;

    private AlarmManager alarmManager;
    private CalendarUtil calendarUtil;
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

    //-------------------------------------Методы использования---------------------------------------//
    // запуск списка будильников
    public void startAlarms(List<Alarm> alarms, Class receiverClass) {
        for (Alarm alarm : alarms) {
            startAlarm(alarm, receiverClass);
        }
    }

    // запуск одного будильника(возращает время(милисекунды) до следующего запуска)
    public long startAlarm(@NonNull Alarm currentAlarm, Class receiverClass) {
        NextAlarmTime nextAlarmTime;
        long nextAlarmTimeMillis;   // время до запуска
        // данные для информации о ближайшем запуске
        byte minDaysToNextAlarm = CalendarUtil.WEEKDAYS_NUMBER;
        byte difference;
        long nextNearestAlarmTimeMillis = 0;

        for (int dayOfWeek = 0; dayOfWeek < currentAlarm.getDays().length; dayOfWeek++) {
            // TODO проверить для всех указанных дней
            if (currentAlarm.getDays()[dayOfWeek]) {    // если выбран день недели
                Intent alarmIntent =
                        createAlarmIntent(
                                appContext,
                                receiverClass,
                                currentAlarm);

                PendingIntent alarmPendingIntent =
                        createAlarmPendingIntent(
                                appContext,
                                getPendingIntentId(currentAlarm.getId(), dayOfWeek),
                                alarmIntent);

                nextAlarmTime =
                        getNextAlarmTimeMillis(
                                dayOfWeek,
                                currentAlarm);  // день недели в alarm с 0

                setAlarmOnTime(
                        nextAlarmTime.getNextAlarmTimeMillis(),
                        alarmPendingIntent,
                        AlarmManager.INTERVAL_DAY * CalendarUtil.WEEKDAYS_NUMBER);  // запустить через неделю


                difference = (byte) CalendarUtil.getToNextAlarmDaysNumber(CalendarUtil.getCurrentDayOfWeek(),
                        CalendarUtil.convertDayOfWeek(dayOfWeek), currentAlarm.getHour(), currentAlarm.getMinute());

                if (difference <= minDaysToNextAlarm) {
                    minDaysToNextAlarm = difference;
                    nextNearestAlarmTimeMillis = nextAlarmTime.getTimeDifferenceMillis();
                }
            }
        }

        return nextNearestAlarmTimeMillis;  // количество милисекунд до первого сигнала
    }

    // удаление всех отложенных намерений списка будильников
    public void deleteAlarms(List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            deleteAlarm(alarm);
        }
    }

    // удаление всех отложенных намерений для данного будильника
    public void deleteAlarm(Alarm alarm) {
        Intent existedAlarmIntent;
        PendingIntent existedAlarmPendingIntent;

        for (int dayOfWeek = 0; dayOfWeek < alarm.getDays().length; dayOfWeek++) {
            if (alarm.getDays()[dayOfWeek]) {    // если выбран день недели
                existedAlarmIntent =
                        createAlarmIntent(
                                appContext,
                                AlarmReceiver.class,
                                alarm);

                existedAlarmPendingIntent =
                        createAlarmPendingIntent(
                                appContext,
                                getPendingIntentId(alarm.getId(), dayOfWeek),
                                existedAlarmIntent);

                alarmClockManager.cancelPendingIntent(existedAlarmPendingIntent);
            }
        }
    }

//--------------------------------------------Специфика-------------------------------------------//
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
    public void setAlarmOnTime(long nextAlarmTimeMillis, PendingIntent alarmPendingIntent) {
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,  // вывести устройство из сна
                    nextAlarmTimeMillis,  // через время
                    alarmPendingIntent);
        }
    }

    // интервальный запуск
    public void setAlarmOnTime(long nextAlarmTimeMillis, PendingIntent alarmPendingIntent, long interval) {
        // TODO проверить работоспособность через неделю
        alarmManager = getAlarmManager();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  // вывести устройство из сна
                nextAlarmTimeMillis,  // через время
                interval,   // повторять каждые
                alarmPendingIntent);
    }

    // перенаправление намерения
    public PendingIntent getRedirectedPendingIntent(Context receiverContext, Class redirectToClass, Intent externalIntent) {
        int redirectedRequestCode;
        Alarm currentAlarm = (Alarm) externalIntent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM);
        if (currentAlarm != null) {
            redirectedRequestCode = getPendingIntentId(currentAlarm.getId(), (int) (Math.random() * 1000));
        } else {
            redirectedRequestCode = getPendingIntentId(0, (int) (Math.random() * 1000));
        }

        // у интента, вызванного AlarmManager-ом изменить класс получатель, сохранив все данные, которые находятся в нём
        externalIntent.setClass(receiverContext, redirectToClass);
        // интент на отмену уведомления
        return PendingIntent.getBroadcast(receiverContext, redirectedRequestCode, externalIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void cancelPendingIntent(PendingIntent existedAlarmPendingIntent) {
        alarmManager = getAlarmManager();
        alarmManager.cancel(existedAlarmPendingIntent);
    }

    //------------------------------------Вспомогательные методы--------------------------------------//
    private int getPendingIntentId(int id, int dayOfWeek) { // id сотавляется из alarmId.append(dayOfWeek)
        return Integer.valueOf(String.valueOf(id) + dayOfWeek);
    }

    private NextAlarmTime getNextAlarmTimeMillis(int dayOfWeek, Alarm currentAlarm) {
        if (calendarUtil == null) {
            Calendar currentCalendar = new GregorianCalendar();
            calendarUtil = CalendarUtil.getInstance(currentCalendar);
        }

        Calendar nextAlarmCalendar =
                calendarUtil.getNextAlarmCalendar(dayOfWeek, currentAlarm.getHour(), currentAlarm.getMinute());

        long nextAlarmTime = nextAlarmCalendar.getTimeInMillis();
        long differenceTime = Math.abs(nextAlarmTime - calendarUtil.getTimeInMillis());

        return new NextAlarmTime(nextAlarmTime, differenceTime);
    }
}
