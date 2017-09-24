package ru.itis.android.events.activities.toplevel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ru.itis.android.events.R;
import ru.itis.android.events.activities.detail.EventDetailActivity;
import ru.itis.android.events.models.Event;

public class EventListActivity extends AppCompatActivity implements OnItemClickListener {
    private static final int TEST_DATA_NUMBER = 10;

    private List<Event> events;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter<EventViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        events = initTestData();
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new EventListAdapter(events, this);

        recyclerView = (RecyclerView) findViewById(R.id.rv_event_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(int position) {
        startActivity(EventDetailActivity.makeIntent(this));
    }

    private List<Event> initTestData() {
        List<Event> events = new ArrayList<>();

        Calendar calendar = new GregorianCalendar(2017, 11, 26);
        for (int i = 0; i < TEST_DATA_NUMBER; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            events.add(new Event(R.drawable.photo2,
                    "Event №" + i,
                    "Description №" + i,
                    calendar.getTime()));
        }
        return events;
    }
}
