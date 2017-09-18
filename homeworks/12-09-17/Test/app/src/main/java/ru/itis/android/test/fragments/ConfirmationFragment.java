package ru.itis.android.test.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import ru.itis.android.test.R;
import ru.itis.android.test.activities.WelcomeActivity;

/**
 * Created by Users on 17.09.2017.
 */

public class ConfirmationFragment extends DialogFragment implements DialogInterface.OnClickListener {
    public static final String MIME_TYPE = "text/plain";

    private String result;
    private boolean restartable;

    public void setResult(String result) {
        this.result = result;
    }

    public void setRestartable(boolean restartable) {
        this.restartable = restartable;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View layout = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_confirmation, null, false);

        return new AlertDialog.Builder(getActivity())
                .setView(layout)
                .setPositiveButton(R.string.btn_positive, this)
                .setNegativeButton(R.string.btn_negative, this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case DialogInterface.BUTTON_POSITIVE:
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType(MIME_TYPE);
                intentShare.putExtra(Intent.EXTRA_TEXT, result);
                startActivity(intentShare);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                if (restartable) {
                    Intent intentRestart = new Intent(getActivity(), WelcomeActivity.class);
                    intentRestart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentRestart);
                } else
                    getActivity().finish();
                break;
        }
    }
}
