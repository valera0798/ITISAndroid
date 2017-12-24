package ru.itis.android.alarmclock.models.database;

import android.database.Cursor;

import java.util.List;

import ru.itis.android.alarmclock.models.Alarm;

/**
 * Created by Users on 25.10.2017.
 */

public interface DBWorker<Entity> {
    void putEntity(Entity entity);

    List<Entity> getEntities();
    int getNewId();
    Entity getElementById(int id);

    void update(Entity currentAlarm, Entity updatedAlarm);

    void deleteAll();
}
