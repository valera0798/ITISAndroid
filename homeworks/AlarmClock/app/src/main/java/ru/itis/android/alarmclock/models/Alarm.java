package ru.itis.android.alarmclock.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Users on 22.10.2017.
 */

public class Alarm implements Parcelable {
    private int id;
    private int hour;
    private int minute;
    private boolean[] days;
    private boolean isActive;

    public Alarm() {
    }

    public Alarm(int id, int hour, int minute, boolean[] days, boolean isActive) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.days = days;
        this.isActive = isActive;
    }

    protected Alarm(Parcel in) {
        id = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
        days = in.createBooleanArray();
        isActive = in.readByte() != 0;
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

    public boolean isActive() {
        return isActive;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setDays(boolean[] days) {
        this.days = days;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Alarm clone() {
        Alarm cloneAlarm = new Alarm();

        cloneAlarm.setId(this.getId());
        cloneAlarm.setHour(this.getHour());
        cloneAlarm.setMinute(this.getMinute());
        cloneAlarm.setDays(this.getDays());
        cloneAlarm.setActive(this.isActive());

        return cloneAlarm;
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(hour);
        parcel.writeInt(minute);
        parcel.writeBooleanArray(days);
        parcel.writeByte((byte) (isActive ? 1 : 0));
    }
}
