package ru.itis.android.events.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Users on 24.09.2017.
 */

public class Event implements Parcelable {
    protected int photoId;
    protected String name;
    protected String description;
    protected Date date;

    public Event() {
    }

    public Event(int photoId, String name, String description, Date date) {
        this.photoId = photoId;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

//--------------------------------------Parcelable------------------------------------------------//
    protected Event(Parcel in) {
        photoId = in.readInt();
        name = in.readString();
        description = in.readString();
        date = (Date) in.readSerializable();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(photoId);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeSerializable(date);
    }
}
