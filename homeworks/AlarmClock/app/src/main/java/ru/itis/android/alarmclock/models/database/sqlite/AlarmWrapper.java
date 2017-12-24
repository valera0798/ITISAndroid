package ru.itis.android.alarmclock.models.database.sqlite;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.sqlite.tables.AlarmsTable;

/**
 * Created by Users on 06.11.2017.
 *
 * AlarmWrapper - одна из специфик реализации БД через SQLite
 *
 */

public class AlarmWrapper extends CursorWrapper {

    public AlarmWrapper(Cursor cursor) {
        super(cursor);
    }

    public List<Alarm> getAlarms() {
        List<Alarm> alarms = new ArrayList<>();
        moveToFirst();
        while (!isBeforeFirst() && !isAfterLast()) {    // пока курсор внутри тела
            alarms.add(getAlarm(getPosition()));
            moveToNext();   // к следующему элементу выборки
        }
        return alarms;
    }

    public Alarm getAlarm(int position) {
        moveToPosition(position);
        if (!isBeforeFirst() && !isAfterLast()) {   // если элемент из тела
            Alarm currentAlarm = new Alarm();
            currentAlarm.setId(getInt(getColumnIndex(AlarmsTable.COLUMN_PRIMARY_KEY_ID)));
            currentAlarm.setHour(getInt(getColumnIndex(AlarmsTable.COLUMN_HOUR)));
            currentAlarm.setMinute(getInt(getColumnIndex(AlarmsTable.COLUMN_MINUTE)));
            currentAlarm.setDays(getDaysFromString(getString(getColumnIndex(AlarmsTable.COLUMN_DAYS))));
            currentAlarm.setActive(convertIntToBoolean(getInt(getColumnIndex(AlarmsTable.COLUMN_IS_ACTIVE))));
            return currentAlarm;
        } else return null;
    }

    //------------------------------------------------------------------------------------------------//
    // Массив days модели Alarm будет храниться в виде строки, где индекс символа строки соотетствует дню недели:
    // [true, false, ...] = 10... (слева направо. 0 - нет будильника, 1 - есть)
    public static boolean[] getDaysFromString(String daysString) {
        boolean[] days = new boolean[daysString.length()];
        for (int i = 0; i < daysString.length(); i++) {
            days[i] = convertCharToBoolean(daysString.charAt(i));
        }
        return days;
    }

    public static String getDaysToString(boolean[] days) {
        StringBuilder stringBuilder = new StringBuilder();
        for (boolean day : days) {
            stringBuilder.append(convertBooleanToChar(day));
        }
        return stringBuilder.toString();
    }

    private static boolean convertCharToBoolean(char c) {
        return c != '0';
    }

    public static char convertBooleanToChar(boolean b) {
        return (b) ? '1' : '0';
    }

    private boolean convertIntToBoolean(int isActiveInt) {
        return (isActiveInt == 1);
    }
}
