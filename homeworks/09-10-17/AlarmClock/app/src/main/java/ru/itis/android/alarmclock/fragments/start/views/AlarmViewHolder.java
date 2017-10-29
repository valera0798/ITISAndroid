package ru.itis.android.alarmclock.fragments.start.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.itis.android.alarmclock.R;
import ru.itis.android.alarmclock.models.Alarm;

/**
 * Created by Users on 22.10.2017.
 */

public class AlarmViewHolder extends RecyclerView.ViewHolder {
    private TextView tvAlarmTime;
    private CheckBox[] cbDays;
    private String[] daysName;

    public AlarmViewHolder(View itemView) {
        super(itemView);

        initViews(itemView);
    }

    private void initViews(View layout) {
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
        tvAlarmTime.setText(String.format("%d : %02d", alarm.getHour(), alarm.getMinute()));

        boolean[] selectedDays = alarm.getDays();
        for (int i = 0; i < cbDays.length; i++) {
            cbDays[i].setChecked(selectedDays[i]);
        }
    }
}
