package ru.itis.android.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_QUESTIONS_NUMBER = "extraQuestionNumber";
    public static final String EXTRA_RIGHT_ANSWERED_QUESTIONS_NUMBER = "extraRightAnsweredQuestionsNumber";
    private static final String KEY_QUESTIONS_NUMBER = "keyQuestionNumber";
    public static final String KEY_RIGHT_ANSWERED_QUESTIONS_NUMBER = "keyRightAnsweredQuestionsNumber";

    private int questionNumber = 0, rightAnsweredQuestionNumber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getData();
        initViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_QUESTIONS_NUMBER, questionNumber);
        outState.putInt(KEY_RIGHT_ANSWERED_QUESTIONS_NUMBER, rightAnsweredQuestionNumber);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        questionNumber = savedInstanceState.getInt(KEY_QUESTIONS_NUMBER);
        rightAnsweredQuestionNumber = savedInstanceState.getInt(KEY_RIGHT_ANSWERED_QUESTIONS_NUMBER);
    }

    private void getData() {
        Intent intent = getIntent();
        questionNumber = intent.getIntExtra(EXTRA_QUESTIONS_NUMBER, 0);
        rightAnsweredQuestionNumber = intent.getIntExtra(EXTRA_RIGHT_ANSWERED_QUESTIONS_NUMBER, 0);
    }

    private void initViews() {
        TextView tvResult = (TextView) findViewById(R.id.tv_result);
        tvResult.setText(tvResult.getText() + " " +
                rightAnsweredQuestionNumber + " / " + questionNumber);
        findViewById(R.id.btn_restart).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
