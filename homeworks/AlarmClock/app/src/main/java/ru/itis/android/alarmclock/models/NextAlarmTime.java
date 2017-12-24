package ru.itis.android.alarmclock.models;

/**
 * Created by Users on 08.11.2017.
 *
 * Класс обёртка данных для запуска будильника и информирования польователя
 *
 */

public class NextAlarmTime {
    private long nextAlarmTimeMillis;
    private long timeDifferenceMillis;

    public NextAlarmTime(long nextAlarmTimeMillis, long timeDifferenceMillis) {
        this.nextAlarmTimeMillis = nextAlarmTimeMillis;
        this.timeDifferenceMillis = timeDifferenceMillis;
    }

    public long getNextAlarmTimeMillis() {
        return nextAlarmTimeMillis;
    }

    public long getTimeDifferenceMillis() {
        return timeDifferenceMillis;
    }
}
