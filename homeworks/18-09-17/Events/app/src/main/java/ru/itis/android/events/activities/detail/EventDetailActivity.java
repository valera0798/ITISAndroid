package ru.itis.android.events.activities.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.itis.android.events.R;

public class EventDetailActivity extends AppCompatActivity {
    public static Intent makeIntent(Context from) {
        Intent intent = new Intent(from, EventDetailActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        // TODO Все круто, но нет работы с ViewPager и pagerIndicator (страницы пустые, но свайпаться должны)
    }
}
