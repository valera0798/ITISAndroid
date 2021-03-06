package ru.itis.android.alarmclock.models.database.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.DBWorker;

/**
 * Created by Users on 25.10.2017.
 */

public class SharedPreferencesManager implements DBWorker<Alarm> {
    // данные каждого будильника в отдельной строке, которая находится в множестве:
    // Пример: alarmId=1,hour=1,minutes=1,isActive=true,Mon=true,...(остальные дни недели по аналогии)

    public static final String APP_PREFERENCES = "alarms";

    private static final String ALARMS = "alarms";  // множество будильников в виде строк
    private static final String ALARM_SERIAL_ID = "alarmSerialId";  // последний сгенерированный ID

    private static final String SEPARATOR = ",";
    private static final String EQUALITY = "=";
    private static final String ALARM_ID = "alarmId=";
    private static final String ALARM_HOURS = "hour=";
    private static final String ALARM_MINUTES = "minutes=";
    private static final String ALARM_ACTIVITY = "isActive=";
    private static final String[] WEEKDAY_NAMES = new String[] {
            "Mon", "Tu", "We", "Th", "Fr", "Sa", "Su"
    };

    private static SharedPreferencesManager sharedPreferencesManager;
    private Context appContext;
    private SharedPreferences alarmsPreferences;
    private SharedPreferences.Editor alarmsEditor;

    public static SharedPreferencesManager getInstance(Context appContext) {
        if (sharedPreferencesManager == null)
            sharedPreferencesManager = new SharedPreferencesManager(appContext);
        return sharedPreferencesManager;
    }
    private SharedPreferencesManager(Context appContext) {
        this.appContext = appContext;
    }

    private SharedPreferences getSharedPreferences() {
        if (alarmsPreferences == null) {
            alarmsPreferences = appContext.getSharedPreferences(
                    APP_PREFERENCES,            // название файла
                    Context.MODE_PRIVATE);      // данные видны только этому приложению
        }
        return alarmsPreferences;
    }
    private SharedPreferences.Editor getEditor() {
        if (alarmsEditor == null) {
            alarmsPreferences = getSharedPreferences();
            alarmsEditor = alarmsPreferences.edit();
        }
        return alarmsEditor;
    }

//-------------------------------Реализация необходимых методов БД--------------------------------//
    @Override
    public List<Alarm> getEntities() {
        List<Alarm> alarmList = new ArrayList<>();
        Set<String> alarmsStringSet = getSharedPreferences().getStringSet(ALARMS, new HashSet<String>());
        for (String alarmString: alarmsStringSet) {
            alarmList.add(alarmFromString(alarmString));
        }
        return alarmList;
    }

    @Override
    public void putEntity(Alarm alarm) {
        incrementSerialId();
        Set<String> alarmsStringSet = getSharedPreferences().getStringSet(ALARMS, new HashSet<String>());
        alarmsStringSet.add(alarmToString(alarm));

        getEditor()
                .putStringSet(ALARMS, alarmsStringSet)
                .apply();
    }

    @Override
    public int getNewId() {
        int currentId = getSharedPreferences().getInt(ALARM_SERIAL_ID, 0);
        return ++currentId;
    }

    @Override
    public void deleteAll() {
        getEditor()
                .clear()
                .apply();
    }

    @Override
    public Alarm getElementById(int id) {
        Set<String> alarmsStringSet = getSharedPreferences().getStringSet(ALARMS, new HashSet<String>());
        for (String alarmString: alarmsStringSet) {
            if (alarmString.contains(ALARM_ID + id))
                return alarmFromString(alarmString);
        }
        return null;
    }

    @Override
    public void update(Alarm currentAlarm, Alarm updatedAlarm) {
        Set<String> alarmsStringSet = getSharedPreferences().getStringSet(ALARMS, new HashSet<String>());

        boolean entryUpdated = false;
        Iterator<String> iterator = alarmsStringSet.iterator();
        while (iterator.hasNext() && !entryUpdated) {
            if (iterator.next().contains(ALARM_ID + currentAlarm.getId())) {
                alarmsStringSet.remove(alarmToString(currentAlarm));
                alarmsStringSet.add(alarmToString(updatedAlarm));
                entryUpdated = true;
            }
        }

        getEditor()
                .putStringSet(ALARMS, alarmsStringSet)
                .apply();
    }

//-----------------------------Специфика реализации БД ч/з SharedPreferences----------------------//
    // специфика: объект Alarm конвертируется в строку, явно описывающую все его поля
    private void incrementSerialId() {
        alarmsPreferences = getSharedPreferences();
        int newSerialId = alarmsPreferences.getInt(ALARM_SERIAL_ID, 0);

        getEditor()
                .putInt(ALARM_SERIAL_ID, ++newSerialId)
                .apply();
    }

    @NonNull
    private String alarmToString(Alarm alarm) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(ALARM_ID + alarm.getId()).append(SEPARATOR)
                .append(ALARM_HOURS + alarm.getHour()).append(SEPARATOR)
                .append(ALARM_MINUTES + alarm.getMinute()).append(SEPARATOR)
                .append(ALARM_ACTIVITY + alarm.isActive()).append(SEPARATOR);

        boolean[] alarmWeekdays = alarm.getDays();
        for (int i = 0; i < WEEKDAY_NAMES.length; i++) {
            stringBuilder
                    .append(WEEKDAY_NAMES[i] + EQUALITY + alarmWeekdays[i]);
            if (i != WEEKDAY_NAMES.length - 1) stringBuilder.append(SEPARATOR);
        }
        return stringBuilder.toString();
    }

    @NonNull
    private Alarm alarmFromString(String alarmString) {
        String[] alarmParams = alarmString.split(SEPARATOR);            // [параметр=значение, ...]
        int id = Integer.valueOf(alarmParams[0].split(EQUALITY)[1]);    // [параметр, значение]
        int hour = Integer.valueOf(alarmParams[1].split(EQUALITY)[1]);
        int minutes = Integer.valueOf(alarmParams[2].split(EQUALITY)[1]);
        boolean isActive = Boolean.valueOf(alarmParams[3].split(EQUALITY)[1]);
        boolean[] weekdays = new boolean[WEEKDAY_NAMES.length];
        for (int i = 0; i < weekdays.length; i++) {
            weekdays[i] = Boolean.valueOf(alarmParams[4 + i].split(EQUALITY)[1]);
        }

        return new Alarm(id, hour, minutes, weekdays, isActive);
    }
}
