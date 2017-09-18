package ru.itis.android.test.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.itis.android.test.R;

public class ResultFragment extends Fragment implements View.OnClickListener {
    public static final String EXTRA_QUESTIONS_NUMBER = "extraQuestionNumber";
    public static final String EXTRA_RIGHT_ANSWERED_QUESTIONS_NUMBER = "extraRightAnsweredQuestionsNumber";
    public static final String TAG_CONFIRMATION_FRAGMENT = "confirmationFragmentTag";

    private int questionNumber = 0, rightAnsweredQuestionNumber = 0;

    public static ResultFragment newInstance(Bundle args) {
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_result, container, false);
        setData(getArguments());
        initViews(layout);
        return layout;
    }

    public void setData(Bundle data) {
        questionNumber = data.getInt(EXTRA_QUESTIONS_NUMBER, 0);
        rightAnsweredQuestionNumber = data.getInt(EXTRA_RIGHT_ANSWERED_QUESTIONS_NUMBER, 0);
    }

    private void initViews(View layout) {
        TextView tvResult = layout.findViewById(R.id.tv_result);
        tvResult.setText(tvResult.getText() + " " +
                rightAnsweredQuestionNumber + " / " + questionNumber);

        layout.findViewById(R.id.btn_restart).setOnClickListener(this);
        layout.findViewById(R.id.btn_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_restart:
                shareResult(true);
                break;
            case R.id.btn_exit:
                shareResult(false);
                break;
        }
    }

    public void shareResult(boolean restartable) {
        ConfirmationFragment confirmationFragment = new ConfirmationFragment();
        confirmationFragment.setResult(getActivity().getString(R.string.share_message1) + " " +
                rightAnsweredQuestionNumber + "/" + questionNumber
                + ". " + getActivity().getString(R.string.share_message2));
        confirmationFragment.setRestartable(restartable);
        confirmationFragment.show(getFragmentManager(), TAG_CONFIRMATION_FRAGMENT);
    }
}
