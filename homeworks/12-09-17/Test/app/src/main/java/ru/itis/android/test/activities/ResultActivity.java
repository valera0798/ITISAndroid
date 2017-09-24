package ru.itis.android.test.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ru.itis.android.test.R;
import ru.itis.android.test.fragments.ResultFragment;
import ru.itis.android.test.fragments.TestFragment;

public class ResultActivity extends FragmentHostActivity {
    public static final String EXTRA_QUESTIONS_NUMBER = "extraQuestionNumber";
    public static final String EXTRA_RIGHT_ANSWERED_QUESTIONS_NUMBER = "extraRightAnsweredQuestionsNumber";

    @Override
    public Fragment getFragment() {
        return ResultFragment.newInstance(getData());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_result;
    }

    @Override
    public int getContainerId() {
        return R.id.fragment_result_container;
    }

    private Bundle getData() {
        Intent intent = getIntent();
        int questionNumber = intent.getIntExtra(EXTRA_QUESTIONS_NUMBER, 0);
        int rightAnsweredQuestionNumber = intent.getIntExtra(EXTRA_RIGHT_ANSWERED_QUESTIONS_NUMBER, 0);

        // фрагмент остается незавизимым от активности
        Bundle dataForFragment = new Bundle();
        dataForFragment.putInt(ResultFragment.EXTRA_QUESTIONS_NUMBER, questionNumber);
        dataForFragment.putInt(ResultFragment.EXTRA_RIGHT_ANSWERED_QUESTIONS_NUMBER, rightAnsweredQuestionNumber);
        return dataForFragment;
    }

    @Override
    public void onBackPressed() {}

    public static Intent makeIntent(Context from, Bundle testData) {
        Intent intent = new Intent(from, ResultActivity.class);
        intent.putExtras(testData);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }
}
