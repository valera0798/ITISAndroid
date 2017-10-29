package ru.itis.android.alarmclock.fragments.start.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.itis.android.alarmclock.R;
import ru.itis.android.alarmclock.fragments.start.views.AlarmViewHolder;
import ru.itis.android.alarmclock.models.Alarm;

/**
 * Created by Users on 22.10.2017.
 */

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmViewHolder> {
    private List<Alarm> alarms;

    public AlarmRecyclerViewAdapter(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_alarm, parent, false);

        return new AlarmViewHolder(item);
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

    public void updateAlarms() {
        this.notifyDataSetChanged();
    }
    public void clear() {
        int size = this.alarms.size();
        this.alarms.clear();
        notifyItemRangeRemoved(0, size);
    }
}
