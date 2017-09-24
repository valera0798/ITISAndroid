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

    // Каждая активность имеет свой специфический макет.
    // В данном случае его стрктура совпадает с activity_fragment_host,
    // Но при последующем развитии, ситуация может измениться.
    public int getLayoutId() {
        return R.layout.activity_fragment_host;
    }

    public int getContainerId() {
        return R.id.fragment_container;
    }

    public abstract Fragment getFragment();

    // Для игнорирования сброса фрагмента из активности.
    // finish() закрывает активность без учёта backstacka.
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
