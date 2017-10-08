package ru.itis.android.lesson_18_09_17.activities.start;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import ru.itis.android.lesson_18_09_17.R;
import ru.itis.android.lesson_18_09_17.activities.start.splash.SplashFragment;

public class StartActivity extends AppCompatActivity {
    private FrameLayout containerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initViews();
        addFragment(SplashFragment.newInstance(), R.id.container_fragment);
    }

    private void addFragment(Fragment fragment, int containerId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(containerId, fragment);
        fragmentTransaction.commit();
    }

    private void initViews() {
        containerFragment = (FrameLayout) findViewById(R.id.container_fragment);
    }
}
