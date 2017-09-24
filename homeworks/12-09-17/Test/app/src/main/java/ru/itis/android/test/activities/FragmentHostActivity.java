package ru.itis.android.test.activities;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.itis.android.test.R;

public abstract class FragmentHostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        preInitialization();

        if (getFragmentManager().findFragmentById(getContainerId()) == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(getContainerId(), getFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    protected void preInitialization() {}

    public int getLayoutId() {
        return R.layout.activity_fragment_host;
    }

    public int getContainerId() {
        return R.id.fragment_container;
    }

    public abstract Fragment getFragment();

    // TODO не совсем понятно, зачем это.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
