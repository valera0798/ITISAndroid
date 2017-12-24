package ru.itis.android.alarmclock.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import ru.itis.android.alarmclock.R;

/**
 * Created by Users on 29.10.2017.
 */

public class ToastManager {
    public static final int DIVIDER_DAY = 1000 * 60 * 60 * 24;
    public static final int DIVIDER_HOUR = 1000 * 60 * 60;
    public static final int DIVIDER_MINUTE = 1000 * 60;
    public static final int DIVIDER_SECOND = 1000;

    private static final String CASE_DAYS = "день";
    private static final String CASE_HOURS = "час";
    private static final String CASE_MINUTES = "минута";
    private static final String CASE_SECONDS = "секунда";

    private Context appContext;

    private static ToastManager toastManager;

    public static ToastManager getInstance(Context appContext) {
        if (toastManager == null)
            toastManager = new ToastManager(appContext);
        return toastManager;
    }

    private ToastManager(Context appContext) {
        this.appContext = appContext;
    }

//------------------------------------------------------------------------------------------------//
    public void showInfo(String message, int lengthLong) {
        Toast.makeText(appContext,
                message,
                lengthLong)
                .show();
    }

    public void showNextAlarmInfo(long nextNearestAlarmTimeMillis) {
        StringBuilder alarmInfoBuilder = new StringBuilder(appContext.getString(R.string.message_info_add_new_alarm))
                .append(" ");

        int days = (int) (nextNearestAlarmTimeMillis / DIVIDER_DAY);
        nextNearestAlarmTimeMillis -= days * DIVIDER_DAY;
        int hours = (int) (nextNearestAlarmTimeMillis / DIVIDER_HOUR);
        nextNearestAlarmTimeMillis -= hours * DIVIDER_HOUR;
        int minutes = (int) (nextNearestAlarmTimeMillis / DIVIDER_MINUTE);
        nextNearestAlarmTimeMillis -= minutes * DIVIDER_MINUTE;
        int second = (int) (nextNearestAlarmTimeMillis / DIVIDER_SECOND);

        createNextAlarmInfoString(alarmInfoBuilder, days, hours, minutes);

        toastManager.showInfo(alarmInfoBuilder.toString(), Toast.LENGTH_SHORT);
    }

    private void createNextAlarmInfoString(StringBuilder alarmInfoBuilder, int days, int hours, int minutes) {
        boolean isFirst = true;
        if (days != 0) {
            append(alarmInfoBuilder, days, getRightCase(CASE_DAYS, days));
            isFirst = false;
        }
        if (hours != 0) {
            if (!isFirst) alarmInfoBuilder.append(", ");
            else isFirst = false;
            append(alarmInfoBuilder, hours, getRightCase(CASE_HOURS, hours));
        }
        if (minutes != 0) {
            if (!isFirst) alarmInfoBuilder.append(", ");
            else isFirst = false;
            append(alarmInfoBuilder, minutes, getRightCase(CASE_MINUTES, minutes));
        } else if (isFirst) {
            append(alarmInfoBuilder, minutes, getRightCase(CASE_MINUTES, minutes));
        }
    }

//----------------------------------Вспомогательные методы----------------------------------------//
    private void append(StringBuilder builder, int number, String relevantName) {
        builder
                .append(number)
                .append(" ")
                .append(relevantName);
    }

    private String getRightCase(String certainCase, int number) {
        String rightCase = "";
        if (number == 1) {
            switch (certainCase) {
                case CASE_DAYS:
                    rightCase = appContext.getString(R.string.message_info_add_new_alarm_days_accusative);
                    break;
                case CASE_HOURS:
                    rightCase = appContext.getString(R.string.message_info_add_new_alarm_hours_accusative);
                    break;
                case CASE_MINUTES:
                    rightCase = appContext.getString(R.string.message_info_add_new_alarm_minutes_accusative);
                    break;
            }
        } else if (number >= 2 && number <= 4) {
            switch (certainCase) {
                case CASE_DAYS:
                    rightCase = appContext.getString(R.string.message_info_add_new_alarm_days_genitive);
                    break;
                case CASE_HOURS:
                    rightCase = appContext.getString(R.string.message_info_add_new_alarm_hours_genitive);
                    break;
                case CASE_MINUTES:
                    rightCase = appContext.getString(R.string.message_info_add_new_alarm_minutes_genitive);
                    break;
            }
        } else if (number >= 5) {
            switch (certainCase) {
                case CASE_DAYS:
                    rightCase = appContext.getString(R.string.message_info_add_new_alarm_days_genitive_plural);
                    break;
                case CASE_HOURS:
                    rightCase = appContext.getString(R.string.message_info_add_new_alarm_hours_genitive_plural);
                    break;
                case CASE_MINUTES:
                    rightCase = appContext.getString(R.string.message_info_add_new_alarm_minutes_genitive_plural);
                    break;
            }
        }
        return rightCase;
    }
}
