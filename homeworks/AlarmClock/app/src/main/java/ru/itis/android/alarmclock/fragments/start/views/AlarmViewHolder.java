package ru.itis.android.alarmclock.fragments.start.views;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ru.itis.android.alarmclock.R;
import ru.itis.android.alarmclock.fragments.start.AlarmListFragment;
import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.models.database.DBWorker;
import ru.itis.android.alarmclock.models.database.shared_preferences.SharedPreferencesManager;
import ru.itis.android.alarmclock.models.database.sqlite.AlarmWrapper;
import ru.itis.android.alarmclock.models.database.sqlite.loaders.AlarmCreateLoader;
import ru.itis.android.alarmclock.models.database.sqlite.loaders.AlarmSelectByIdLoader;
import ru.itis.android.alarmclock.models.database.sqlite.loaders.AlarmUpdateLoader;
import ru.itis.android.alarmclock.receiver.AlarmReceiver;
import ru.itis.android.alarmclock.utils.AlarmClockManager;
import ru.itis.android.alarmclock.utils.ToastManager;

import static ru.itis.android.alarmclock.fragments.start.AlarmListFragment.LOADER_ID_SELECT_BY_ID;

/**
 * Created by Users on 22.10.2017.
 */

public class AlarmViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener, LoaderManager.LoaderCallbacks<Cursor> {


    private Switch switchOnOffAlarm;
    private TextView tvAlarmId;
    private TextView tvAlarmTime;
    private CheckBox[] cbDays;
    private String[] daysName;

    private DBWorker<Alarm> dbWorker;
    private AlarmListFragment fragment;

    public AlarmViewHolder(View itemView, DBWorker dbWorker, Fragment fragment) {
        super(itemView);

        //this.dbWorker = SharedPreferencesManager.getInstance(itemView.getContext());
        this.dbWorker = dbWorker;
        this.fragment = ((AlarmListFragment) fragment);
        initViews(itemView);
    }

    private void initViews(View layout) {
        switchOnOffAlarm = layout.findViewById(R.id.switch_alarm);

        tvAlarmId = layout.findViewById(R.id.tv_alarm_id);
        tvAlarmTime = layout.findViewById(R.id.tv_alarm_time);
        LinearLayout containerButtons = layout.findViewById(R.id.list_container_buttons);

        daysName = layout.getResources().getStringArray(R.array.week_days);
        cbDays = new CheckBox[daysName.length];
        RelativeLayout dayLayout;
        for (int i = 0; i < daysName.length; i++) {
            dayLayout = (RelativeLayout) containerButtons.getChildAt(i);
            cbDays[i] = dayLayout.findViewById(R.id.sample_cb_day);
            cbDays[i].setEnabled(false);
            ((TextView) dayLayout.findViewById(R.id.sample_tv_day_name)).setText(daysName[i]);
        }
    }

    public void bind(Alarm alarm) {
        tvAlarmId.setText(String.valueOf(alarm.getId()));
        tvAlarmTime.setText(String.format("%d : %02d", alarm.getHour(), alarm.getMinute()));

        switchOnOffAlarm.setOnCheckedChangeListener(null);

        fragment.readAlarmByIdAsync(alarm.getId(), this);

        boolean[] selectedDays = alarm.getDays();
        for (int i = 0; i < cbDays.length; i++) {
            cbDays[i].setChecked(selectedDays[i]);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        AlarmClockManager alarmClockManager = AlarmClockManager.getInstance(compoundButton.getContext());
        ToastManager toastManager = ToastManager.getInstance(compoundButton.getContext());
        Alarm currentAlarm = dbWorker.getElementById(
                Integer.valueOf(tvAlarmId.getText().toString()));
        Alarm updatedAlarm = currentAlarm.clone();

        if (compoundButton.isChecked()) {
            long nextAlarmMillis = alarmClockManager
                    .startAlarm(currentAlarm,
                                AlarmReceiver.class);
            updatedAlarm.setActive(true);

            fragment.updateAlarm(currentAlarm, updatedAlarm);

            toastManager.showNextAlarmInfo(nextAlarmMillis);
        } else {
            alarmClockManager
                    .deleteAlarm(currentAlarm);

            updatedAlarm.setActive(false);
            fragment.updateAlarm(currentAlarm, updatedAlarm);

            toastManager
                    .showInfo(compoundButton.getContext().getString(R.string.message_info_delete_alarm),
                            Toast.LENGTH_SHORT);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID_SELECT_BY_ID:
                return new AlarmSelectByIdLoader(fragment.getActivity(), dbWorker, args);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_ID_SELECT_BY_ID:
                configureSwitch(new AlarmWrapper(data).getAlarm(0));
                break;
        }
    }

    private void configureSwitch(Alarm boundAlarm) {
        switchOnOffAlarm.setChecked(boundAlarm.isActive());
        switchOnOffAlarm.setOnCheckedChangeListener(this);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
