package ru.itis.android.lesson_18_09_17.activities.start.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.itis.android.lesson_18_09_17.R;
import ru.itis.android.lesson_18_09_17.activities.contactlist.ContactsListActivity;

public class SplashFragment extends Fragment {
    private static final int MAX_VALUE = 10;
    private static final int REQUIRED_LOADING_TIME_MILLIS = 2000;
    private static final int DELAY = REQUIRED_LOADING_TIME_MILLIS / MAX_VALUE;

    private ProgressBar pbLoading;
    private TextView tvLoading;
    private Handler progressHandler;
    private int progress;

    public static Fragment newInstance() {
        return new SplashFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_splash, container, false);
        initViews(layout);

        progressHandler = new Handler();
        progress = 0;
        startLoading();

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews(View layout) {
        pbLoading = layout.findViewById(R.id.pb_loading);
        tvLoading = layout.findViewById(R.id.tv_loading);
    }

    private void startLoading() {
        progressHandler.post(new Runnable() {
            @Override
            public void run() {
                if (progress < MAX_VALUE) {
                    changeProgressState();
                    progressHandler.postDelayed(this, DELAY);
                } else {
                    Intent intent = ContactsListActivity.makeIntent(getActivity());
                    startActivity(intent);
                }
            }
        });
    }
    private void changeProgressState() {
        pbLoading.setProgress(progress++);
        if (progress % 3 != 0)
            tvLoading.setText(tvLoading.getText() + ".");
        else tvLoading.setText(getString(R.string.tv_loading));
    }
}
