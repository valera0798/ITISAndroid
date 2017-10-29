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
        StringBuilder alarmInfoBuilder = new StringBuilder(appContext.getString(R.string.message_info_add_new_alarm));

        int days = (int) (nextNearestAlarmTimeMillis / DIVIDER_DAY);
        nextNearestAlarmTimeMillis -= days * DIVIDER_DAY;
        int hours = (int) (nextNearestAlarmTimeMillis / DIVIDER_HOUR);
        nextNearestAlarmTimeMillis -= hours * DIVIDER_HOUR;
        int minutes = (int) (nextNearestAlarmTimeMillis / DIVIDER_MINUTE);
        nextNearestAlarmTimeMillis -= minutes * DIVIDER_MINUTE;
        int second = (int) (nextNearestAlarmTimeMillis / DIVIDER_SECOND);

        createNextAlarmInfoString(alarmInfoBuilder, days, hours, minutes);

        toastManager.showInfo(alarmInfoBuilder.toString(), Toast.LENGTH_LONG);
    }

    private void createNextAlarmInfoString(StringBuilder alarmInfoBuilder, int days, int hours, int minutes) {
        boolean isFirst = true;
        if (days != 0) {
            alarmInfoBuilder.append(days + appContext.getString(R.string.message_info_add_new_alarm_days));
            isFirst = false;
        }
        if (hours != 0) {
            if (!isFirst) alarmInfoBuilder.append(", ");
            else isFirst = false;
            alarmInfoBuilder
                    .append(hours + appContext.getString(R.string.message_info_add_new_alarm_hours));
        }
        if (minutes != 0)
            if (!isFirst) alarmInfoBuilder.append(", ");
            else isFirst = false;
        alarmInfoBuilder
                .append(minutes + appContext.getString(R.string.message_info_add_new_alarm_minutes));
    }
}
