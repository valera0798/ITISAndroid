package ru.itis.android.alarmclock.fragments.start;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import ru.itis.android.alarmclock.R;
import ru.itis.android.alarmclock.models.Alarm;

public class CreateAlarmFragment extends DialogFragment implements DialogInterface.OnClickListener,
        CompoundButton.OnCheckedChangeListener, DialogInterface.OnShowListener {
    public static final String EXTRA_ALARM = "EXTRA_ALARM";

    private AlertDialog createAlarmDialog;
    private View alarmCreatorLayout;
    private TimePicker tpAlarmTime;
    private CheckBox[] cbDays;
    private String[] daysName;

    private int alarmId;

    public static CreateAlarmFragment newInstance(int currentAlarmId) {
        CreateAlarmFragment createAlarmFragment = new CreateAlarmFragment();
        createAlarmFragment.setAlarmId(currentAlarmId);

        return createAlarmFragment;
    }
    private void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        alarmCreatorLayout = inflater.inflate(R.layout.fragment_create_alarm, null);
        daysName = new String[alarmCreatorLayout.getResources().getInteger(R.integer.week_days_number)];
        initView();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        createAlarmDialog = builder
                .setView(alarmCreatorLayout)
                .setPositiveButton(getString(R.string.btn_create_alarm), this)
                .setCancelable(false)
                .create();
        createAlarmDialog.setOnShowListener(this);

        return createAlarmDialog;
    }
    private void initView() {
        tpAlarmTime = alarmCreatorLayout.findViewById(R.id.tp_time_alarm);
        tpAlarmTime.setIs24HourView(true);
        LinearLayout llContainerButtons = alarmCreatorLayout.findViewById(R.id.ll_container_buttons);

        daysName = alarmCreatorLayout.getResources().getStringArray(R.array.week_days);
        cbDays = new CheckBox[daysName.length];
        RelativeLayout dayLayout;
        for (int i = 0; i < daysName.length; i++) {
            dayLayout = (RelativeLayout) llContainerButtons.getChildAt(i);
            cbDays[i] = dayLayout.findViewById(R.id.sample_cb_day);
            ((TextView) dayLayout.findViewById(R.id.sample_tv_day_name)).setText(daysName[i]);
            cbDays[i].setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        sendResult(Activity.RESULT_OK);
    }
    public void sendResult(int resultCode) {
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_ALARM, createAlarm());

            getTargetFragment()
                    .onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }
    private Alarm createAlarm() {
        int hour = tpAlarmTime.getCurrentHour(),
            minute = tpAlarmTime.getCurrentMinute();

        boolean[] selectedDays = new boolean[daysName.length];
        for (int i = 0; i < daysName.length; i++) {
            selectedDays[i] = cbDays[i].isChecked();
        }

        return new Alarm(alarmId, hour, minute, selectedDays, true);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (isAnythingChecked()) {
            createAlarmDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
        } else {
            createAlarmDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }
    private boolean isAnythingChecked() {
        boolean isAnythingChecked = false;
        for (CheckBox cbDay: cbDays) {
            if (cbDay.isChecked()) isAnythingChecked = true;
        }
        return isAnythingChecked;
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        createAlarmDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
    }
}
