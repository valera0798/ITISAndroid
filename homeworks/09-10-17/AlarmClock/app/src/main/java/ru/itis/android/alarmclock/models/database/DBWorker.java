package ru.itis.android.alarmclock.models.database;

import java.util.List;

/**
 * Created by Users on 25.10.2017.
 */

public interface DBWorker<Entity> {
    List<Entity> getEntities();
    void putEntity(Entity entity);
    int getNewId();
    void deleteAll();
}
