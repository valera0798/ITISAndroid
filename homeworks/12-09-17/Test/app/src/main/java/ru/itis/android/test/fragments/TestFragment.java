package ru.itis.android.test.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.itis.android.test.R;
import ru.itis.android.test.activities.FragmentHostActivity;
import ru.itis.android.test.activities.ResultActivity;
import ru.itis.android.test.models.Question;
import ru.itis.android.test.models.Test;

public class TestFragment extends Fragment implements View.OnClickListener {

    public static final String RB_ANSWER_PATTERN = "rb_answer";
    public static final String RB_ID_RESOURCE_TYPE = "id";

    private int containerId;
    private View layout;
    private TextView tvQuestionNumber;
    private TextView tvQuestionText;
    private RadioGroup rgAnswers;
    private List<RadioButton> rbAnswers;
    private Button btnAnswer;

    private Test test;

    public static TestFragment newInstance(Test test, int containerId) {
        TestFragment testFragment = new TestFragment();
        testFragment.setContainerId(containerId);
        testFragment.setTest(test);
        return testFragment;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fargment_test, container, false);
        initViews();
        updateQuestion(test.getCurrentQuestion());

        return layout;
    }

    private void initViews() {
        tvQuestionNumber = layout.findViewById(R.id.tv_question_number);
        tvQuestionText = layout.findViewById(R.id.tv_question_text);
        rgAnswers = layout.findViewById(R.id.rg_answers);

        rbAnswers = new ArrayList<>();
        int rbId;
        for (int i = 1; i <= rgAnswers.getChildCount(); i++) {
            rbId = getResources().getIdentifier(RB_ANSWER_PATTERN + i,
                    RB_ID_RESOURCE_TYPE,
                    getActivity().getPackageName());
            rbAnswers.add((RadioButton) layout.findViewById(rbId));
        }

        btnAnswer = layout.findViewById(R.id.btn_answer);
        btnAnswer.setOnClickListener(this);
    }

    // обновление элементов активности при переходе к следующему вопросу
    private void updateQuestion(Question firstQuestion) {
        updateViews(firstQuestion);
    }
    private void updateQuestion() {
        if (!test.isEnded()) {
            test.getNextQuestion();
            nextTestFragment();
        } else startResultActivity();
    }

    private void nextTestFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(containerId,
                        TestFragment.newInstance(test, containerId))
                .addToBackStack(null)
                .commit();
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

    private void startResultActivity() {
        Bundle testData = new Bundle();
        testData.putInt(ResultActivity.EXTRA_QUESTIONS_NUMBER, test.getQuestionsNumber());
        testData.putInt(ResultActivity.EXTRA_RIGHT_ANSWERED_QUESTIONS_NUMBER,
                test.getRightAnsweredQuestionsNumber());

        startActivity(ResultActivity.makeIntent(getActivity(), testData));
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

    @Override
    public void onStart() {
        super.onStart();
        rgAnswers.clearCheck();
    }

    @Override
    public void onClick(View view) {
        RadioButton rbChecked = layout.findViewById(rgAnswers.getCheckedRadioButtonId());
        if (rbChecked != null) {
            Question currentQuestion = test.getCurrentQuestion();
            currentQuestion.answer(rbChecked.getText().toString());
            test.verifyCorrectness(currentQuestion);
            updateQuestion();
        } else
            Toast.makeText(getActivity(), R.string.error_not_checked, Toast.LENGTH_SHORT).show();
    }
}
