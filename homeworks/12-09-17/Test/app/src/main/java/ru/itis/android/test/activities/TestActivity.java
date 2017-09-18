package ru.itis.android.test.activities;

import android.app.Fragment;
import android.app.FragmentManager;

import ru.itis.android.test.R;
import ru.itis.android.test.fragments.TestFragment;
import ru.itis.android.test.models.Test;

public class TestActivity extends FragmentHostActivity {
    public static final String SEPARATOR = "/"; // то, чем разделены овтеты в строковом ресурсе
    public static final int RIGHT_ANSWER_INDEX = 0; // индекс правильного ответа в строковом ресурсе

    private Test test;

    @Override
    protected void preInitialization() {
        super.preInitialization();
        test = new Test(getResources().getStringArray(R.array.array_questions),
                getResources().getStringArray(R.array.array_answers),
                SEPARATOR,
                RIGHT_ANSWER_INDEX);
    }

    @Override
    public Fragment getFragment() {
        return TestFragment.newInstance(test);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public int getContainerId() {
        return R.id.fragment_test_container;
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
            test.getPrevQuestion();
        } else if (getFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        }
    }
}
