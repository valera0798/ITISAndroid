package ru.itis.android.alarmclock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Arrays;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.receiver.responder.NotificationResponder;
import ru.itis.android.alarmclock.receiver.responder.Responder;
import ru.itis.android.alarmclock.utils.AlarmClockManager;
import ru.itis.android.alarmclock.utils.CalendarUtil;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String LOG_D_ALARM_RECEIVER = "LOG_D_ALARM_RECEIVER";

    private Responder responder;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_D_ALARM_RECEIVER, "Received intent with action: " + intent.getAction());
        Alarm currentAlarm = (Alarm) intent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM);
        Log.d(LOG_D_ALARM_RECEIVER, "Received intent with extra: " + currentAlarm);
        if (currentAlarm != null) {
            Log.d(LOG_D_ALARM_RECEIVER, "Received intent with id: " +
                    currentAlarm.getId());
            Log.d(LOG_D_ALARM_RECEIVER, "Received intent with time: " +
                    currentAlarm.getHour() + " : " + currentAlarm.getMinute());
            Log.d(LOG_D_ALARM_RECEIVER, "Received intent with days: " +
                    Arrays.toString(currentAlarm.getDays()));
        }
        Log.d(LOG_D_ALARM_RECEIVER, "----------------------------------------");

        if (intent.getAction().equals(AlarmClockManager.ACTION_CREATE_NEW_ALARM) &&
                intent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM) != null) { // если получен будильник, созданный в AlarmClock

            if (CalendarUtil.isRightTime(((Alarm) intent.getSerializableExtra(AlarmClockManager.EXTRA_CURRENT_ALARM)))) {
                if (responder == null)
                    responder = NotificationResponder.getInstance(context, intent);
                responder.response();
            }
        }
    }
}
