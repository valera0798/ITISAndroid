package ru.itis.android.alarmclock.activities.start;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import ru.itis.android.alarmclock.R;
import ru.itis.android.alarmclock.activities.FragmentHostActivity;
import ru.itis.android.alarmclock.fragments.start.AlarmListFragment;
import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.DBWorker;
import ru.itis.android.alarmclock.models.database.shared_preferences.SharedPreferencesManager;
import ru.itis.android.alarmclock.models.database.sqlite.SQLiteManager;

public class AlarmClockActivity extends FragmentHostActivity {
    private DBWorker<Alarm> dbWorker;

    @Override
    protected void preInitialization() {
        super.preInitialization();
        //dbWorker = SharedPreferencesManager.getInstance(this);
        dbWorker = SQLiteManager.getInstance(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_alarm_clock;
    }

    @Override
    public int getContainerId() {
        return R.id.container_main_fragment;
    }

    @Override
    public Fragment getFragment() {
        return AlarmListFragment.newInstance(dbWorker, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_entries:
                ((AlarmListFragment) getSupportFragmentManager().findFragmentById(getContainerId())).deleteAllAlarms();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
