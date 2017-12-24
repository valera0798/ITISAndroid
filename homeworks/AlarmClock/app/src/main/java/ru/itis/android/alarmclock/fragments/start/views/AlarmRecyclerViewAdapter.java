package ru.itis.android.alarmclock.fragments.start.views;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.itis.android.alarmclock.R;
import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.DBWorker;

/**
 * Created by Users on 22.10.2017.
 */

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmViewHolder> {
    private List<Alarm> alarms;
    private DBWorker dbWorker;
    private Fragment fragment;

    public AlarmRecyclerViewAdapter(List<Alarm> alarms, DBWorker dbWorker, Fragment fragment) {
        this.alarms = alarms;
        this.dbWorker = dbWorker;
        this.fragment = fragment;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_alarm, parent, false);

        return new AlarmViewHolder(item, dbWorker, fragment);
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.bind(alarm);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

//-----------------------------------Методы для удобного использования----------------------------//
    public void updateAlarms() {
        this.notifyDataSetChanged();
    }

    public void clear() {
        int size = this.alarms.size();
        this.alarms.clear();
        notifyItemRangeRemoved(0, size);
    }
}
