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

    // TODO зачем было переопределять эти методы, когда все это уже есть в activity_fragment_host
    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    // TODO зачем было переопределять эти методы, когда все это уже есть в activity_fragment_host
    @Override
    public int getContainerId() {
        return R.id.fragment_welcome_container;
    }
}
