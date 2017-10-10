package ru.itis.android.events.activities.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import ru.itis.android.events.R;
import ru.itis.android.events.models.Event;

public class EventDetailActivity extends AppCompatActivity {
    private static final String EXTRA_EVENT_LIST = "extraEventList";
    private static final String EXTRA_POSITION = "extraPosition";

    private CircleIndicator ciIndicator;
    private ViewPager vpPager;
    private FragmentStatePagerAdapter vpAdapter;

    private ArrayList<Event> eventList;
    private int position;

    public static Intent makeIntent(Context from, int position, ArrayList<Event> eventList) {
        Intent intent = new Intent(from, EventDetailActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_EVENT_LIST, eventList);
        intent.putExtra(EXTRA_POSITION, position);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // TODO Все круто, но нет работы с ViewPager и pagerIndicator (страницы пустые, но свайпаться должны)
        getData(getIntent());
        initViews();
    }

    private void getData(Intent intent) {
        eventList = intent.getParcelableArrayListExtra(EXTRA_EVENT_LIST);
        position = intent.getIntExtra(EXTRA_POSITION, 0);
    }

    private void initViews() {
        vpAdapter = new EventPagerAdapter(getSupportFragmentManager(), eventList);
        ciIndicator = (CircleIndicator) findViewById(R.id.ci_indicator);

        vpPager = (ViewPager) findViewById(R.id.vp_pager);
        vpPager.setAdapter(vpAdapter);
        vpPager.setCurrentItem(position);

        ciIndicator.setViewPager(vpPager);
    }
}
