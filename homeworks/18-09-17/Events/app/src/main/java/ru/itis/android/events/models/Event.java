package ru.itis.android.events.models;

import java.util.Date;

/**
 * Created by Users on 24.09.2017.
 */

public class Event {
    private int photoId;
    private String name;
    private String description;
    private Date date;

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
}
