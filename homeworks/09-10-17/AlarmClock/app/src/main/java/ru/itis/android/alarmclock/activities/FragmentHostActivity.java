package ru.itis.android.alarmclock.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import ru.itis.android.alarmclock.R;

public abstract class FragmentHostActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        preInitialization();

        if (getSupportFragmentManager().findFragmentById(getContainerId()) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(getContainerId(), getFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    protected void preInitialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public abstract int getLayoutId();

    public abstract int getContainerId();

    public abstract Fragment getFragment();

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
