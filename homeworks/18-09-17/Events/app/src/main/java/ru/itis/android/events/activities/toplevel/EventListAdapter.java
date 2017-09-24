package ru.itis.android.events.activities.toplevel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.itis.android.events.R;
import ru.itis.android.events.models.Event;

/**
 * Created by Users on 24.09.2017.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private List<Event> events;
    private OnItemClickListener onItemClickListener;

    public EventListAdapter(List<Event> events, OnItemClickListener onItemClickListener) {
        this.events = events;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_event, parent, false);
        return new EventViewHolder(layout, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
