package ru.itis.android.test.activities;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ru.itis.android.test.R;
import ru.itis.android.test.fragments.WelcomeFragment;

public class WelcomeActivity extends FragmentHostActivity {
    @Override
    public Fragment getFragment() {
        return WelcomeFragment.newInstance();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public int getContainerId() {
        return R.id.fragment_welcome_container;
    }
}
