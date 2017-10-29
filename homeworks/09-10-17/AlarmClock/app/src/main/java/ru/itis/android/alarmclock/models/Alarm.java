package ru.itis.android.alarmclock.models;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Users on 22.10.2017.
 */

public class Alarm implements Serializable {
    private int id;
    private int hour;
    private int minute;
    private boolean[] days;

    public Alarm(int id, int hour, int minute, boolean[] days) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean[] getDays() {
        return days;
    }
}
