package ru.itis.android.alarmclock.utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.itis.android.alarmclock.models.Alarm;

/**
 * Created by Users on 30.10.2017.
 */

public class CalendarUtil {
    public static final int WEEKDAYS_NUMBER = 7;

    private static CalendarUtil calendarUtil;

    private Calendar currentCalendar;

    public static CalendarUtil getInstance(Calendar currentCalendar) {
        if (calendarUtil == null)
            calendarUtil = new CalendarUtil(currentCalendar);
        return calendarUtil;
    }
    private CalendarUtil(Calendar currentCalendar) {
        this.currentCalendar = currentCalendar;
    }

//--------------------------------------Основные методы-------------------------------------------//
    public Calendar getNextAlarmCalendar(int dayOfWeek, int hour, int minute) {
        int daysToNextAlarm = getToNextAlarmDaysNumber(
                currentCalendar.get(Calendar.DAY_OF_WEEK),
                convertDayOfWeek(dayOfWeek),
                hour, minute);

        int nextAlarmDay = currentCalendar.get(Calendar.DAY_OF_MONTH) + daysToNextAlarm,
            nextAlarmMonth = currentCalendar.get(Calendar.MONTH),
            nextAlarmYear = currentCalendar.get(Calendar.YEAR);

        if (isNextMonth(nextAlarmDay)) {
            nextAlarmDay %= currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            nextAlarmMonth = currentCalendar.get(Calendar.MONTH) + 1;
            if (isNextYear(nextAlarmMonth)) {
                nextAlarmMonth %= currentCalendar.getActualMaximum(Calendar.MONTH);
                nextAlarmYear = currentCalendar.get(Calendar.YEAR) + 1;
            }
        }

        Calendar newAlarmCalendar = new GregorianCalendar(nextAlarmYear, nextAlarmMonth, nextAlarmDay);

        newAlarmCalendar.set(Calendar.HOUR, hour);
        newAlarmCalendar.set(Calendar.MINUTE, minute);
        newAlarmCalendar.set(Calendar.SECOND, currentCalendar.get(Calendar.SECOND));
        newAlarmCalendar.set(Calendar.MILLISECOND, currentCalendar.get(Calendar.MILLISECOND));

        return newAlarmCalendar;
    }

    public static int convertDayOfWeek(int dayOfWeek) {
        return (dayOfWeek + 2) % WEEKDAYS_NUMBER;
    }

    public static int getToNextAlarmDaysNumber(int currentDayOfWeek, int nextDayOfWeek, int hour, int minute) {
        if (currentDayOfWeek > nextDayOfWeek)   // на следующей неделе
            return WEEKDAYS_NUMBER - currentDayOfWeek + nextDayOfWeek;
        else if (currentDayOfWeek < nextDayOfWeek)  // на этой неделе
            return nextDayOfWeek - currentDayOfWeek;
        else if (currentDayOfWeek == nextDayOfWeek && onNextWeek(hour, minute)) // на следующей неделе в этот же день
            return WEEKDAYS_NUMBER ;
        else    // сегодня
            return 0;
    }

    public static int getCurrentDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    public long getTimeInMillis() {
        return currentCalendar.getTimeInMillis();
    }

    public static boolean isRightTime(Alarm alarm) {
        Calendar currentCalendar = Calendar.getInstance();

        boolean isRightTime = false;
        int currentDay = currentCalendar.get(Calendar.DAY_OF_WEEK);
        int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(Calendar.MINUTE);

        for (int i = 0; i < alarm.getDays().length && !isRightTime; i++) {
            if (convertDayOfWeek(i) == currentDay)
                isRightTime = true;
        }
        isRightTime &= currentHour == alarm.getHour();
        isRightTime &= currentMinute == alarm.getMinute() || currentMinute == alarm.getMinute() + 1;
                //((alarm.getMinute() - 2) <= currentMinute) && (currentMinute <= (alarm.getMinute() + 2));
        return isRightTime;
    }

//-------------------------------Вспомогательные методы-------------------------------------------//
    private boolean isNextMonth(int daysToNextAlarm) {
        return daysToNextAlarm > currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private boolean isNextYear(int nextAlarmMonth) {
        return nextAlarmMonth > currentCalendar.getActualMaximum(Calendar.MONTH);
    }

    private static boolean onNextWeek(int hour, int minute) {
        Calendar currentCalendar = Calendar.getInstance();
        int currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(Calendar.MINUTE);

        if (currentHour > hour)
            return true;
        else if (currentHour < hour)
            return false;
        else {  // currentHour == hour
            if (currentMinute > minute)
                return true;
            else if (currentMinute < minute)
                return false;
            else // currentMinute == minute
                return false;
        }
    }
}
