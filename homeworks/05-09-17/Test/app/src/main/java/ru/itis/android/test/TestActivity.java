package ru.itis.android.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.itis.android.test.models.ParcelableTest;
import ru.itis.android.test.models.Question;
import ru.itis.android.test.models.Test;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String SEPARATOR = "/"; // то, чем разделены овтеты в строковом ресурсе
    public static final int RIGHT_ANSWER_INDEX = 0; // индекс правильного ответа в строковом ресурсе
    public static final String RB_ANSWER_PATTERN = "rb_answer";
    public static final String RB_ID_RESOURCE_TYPE = "id";
    private static final String KEY_TEST = "keyTest";

    private TextView tvQuestionNumber;
    private TextView tvQuestionText;
    private RadioGroup rgAnswers;
    private List<RadioButton> rbAnswers;
    private Button btnAnswer;
    private Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initViews();
        test = new Test(getResources().getStringArray(R.array.array_questions),
                getResources().getStringArray(R.array.array_answers), SEPARATOR, RIGHT_ANSWER_INDEX);
        updateQuestion(test.getCurrentQuestion());
    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable(KEY_TEST, test);
//    }
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        test = savedInstanceState.getParcelable(KEY_TEST);
//    }

    private void initViews() {
        tvQuestionNumber = (TextView) findViewById(R.id.tv_question_number);
        tvQuestionText = (TextView) findViewById(R.id.tv_question_text);
        rgAnswers = (RadioGroup) findViewById(R.id.rg_answers);

        rbAnswers = new ArrayList<>();
        int rbId;
        for (int i = 1; i <= rgAnswers.getChildCount(); i++) {
            rbId = getResources().getIdentifier(RB_ANSWER_PATTERN + i,
                    RB_ID_RESOURCE_TYPE,
                    this.getPackageName());
            rbAnswers.add((RadioButton) findViewById(rbId));
        }

        btnAnswer = (Button) findViewById(R.id.btn_answer);
        btnAnswer.setOnClickListener(this);
    }

    // обновление элементов активности при переходе к следующему вопросу
    private void updateQuestion(Question firstQuestion) {
        updateViews(firstQuestion);
    }
    private void updateQuestion() {
        if (!test.isEnded()) {
            Question currentQuestion = test.getNextQuestion();
            updateViews(currentQuestion);
        } else startResultActivity();
    }
    private void updateViews(Question currentQuestion) {
        tvQuestionNumber.setText("№ " + (test.getCurrentQuestionIndex() + 1) + " / "
                                            + test.getQuestionsNumber());
        tvQuestionText.setText(currentQuestion.getText());
        rgAnswers.clearCheck();

        String[] answers = currentQuestion.getAnswers();
        shuffleQuestions(answers);
        for (int i = 0; i < answers.length; i++) rbAnswers.get(i).setText(answers[i]);
    }
    private void shuffleQuestions(String[] answers) {
        int toIndex, fromIndex;
        String tmp;
        for (int i = 0; i < answers.length; i++) {
            toIndex = (int) (Math.random()*answers.length);
            fromIndex = (int) (Math.random()*answers.length);
            tmp = answers[toIndex];
            answers[toIndex] = answers[fromIndex];
            answers[fromIndex] = tmp;
        }
    }

    private void startResultActivity() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(ResultActivity.EXTRA_QUESTIONS_NUMBER, test.getQuestionsNumber());
        intent.putExtra(ResultActivity.EXTRA_RIGHT_ANSWERED_QUESTIONS_NUMBER,
                test.getRightAnsweredQuestionsNumber());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        RadioButton rbChecked = (RadioButton) findViewById(rgAnswers.getCheckedRadioButtonId());
        Question currentQuestion = test.getCurrentQuestion();
        currentQuestion.answer(rbChecked.getText().toString());
        test.verifyCorrectness(currentQuestion);
        updateQuestion();
    }
}
