package ru.itis.android.test.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.itis.android.test.R;
import ru.itis.android.test.activities.TestActivity;

public class WelcomeFragment extends Fragment implements View.OnClickListener{
    private Button btnStart;

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_welcome, container, false);
        initViews(layout);
        return layout;
    }

    private void initViews(View layout) {
        btnStart = layout.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(TestActivity.makeIntent(view.getContext()));
    }
}
