package ru.itis.android.alarmclock.fragments.start;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import ru.itis.android.alarmclock.R;
import ru.itis.android.alarmclock.fragments.start.views.AlarmRecyclerViewAdapter;
import ru.itis.android.alarmclock.models.database.DBWorker;
import ru.itis.android.alarmclock.receiver.AlarmReceiver;
import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.utils.AlarmClockManager;
import ru.itis.android.alarmclock.utils.ToastManager;

public class AlarmListFragment extends Fragment implements View.OnClickListener{

    private static final int REQUEST_CODE_CREATE_ALARM = 1;

    private static final String TAG_CREATE_ALARM = "TAG_CREATE_ALARM";
    private static final String ALARM_CREATE_TAG = "ALARM_CREATE_TAG";

    private List<Alarm> alarms;

    private AlarmClockManager alarmClockManager;
    private FragmentManager fragmentManager;
    private ToastManager toastManager;
    private DBWorker dbWorker;

    private RecyclerView rvAlarmList;
    private AlarmRecyclerViewAdapter rvAlarmListAdapter;
    private RecyclerView.LayoutManager rvLayoutManage;
    private FloatingActionButton btnCreateAlarm;

    public static AlarmListFragment newInstance(DBWorker<Alarm> dbWorker, Context appContext) {
        AlarmListFragment fragment = new AlarmListFragment();
        fragment.setAlarms(dbWorker.getEntities());
        fragment.setAlarmClockManager(AlarmClockManager.getInstance(appContext));
        fragment.setToastManager(ToastManager.getInstance(appContext));
        fragment.setDBWorker(dbWorker);

        return fragment;
    }
    private void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
    private void setAlarmClockManager(AlarmClockManager alarmClockManager) {
        this.alarmClockManager = alarmClockManager;
    }
    private void setToastManager(ToastManager toastManager) {
        this.toastManager = toastManager;
    }
    private void setDBWorker(DBWorker dbWorker) {
        this.dbWorker = dbWorker;
    }

    public AlarmListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        initViews(layout);
        return layout;
    }
    private void initViews(View layout) {
        btnCreateAlarm = layout.findViewById(R.id.btn_create_alarm);
        btnCreateAlarm.setOnClickListener(this);
        rvAlarmList = layout.findViewById(R.id.rv_alarm_list);
        rvAlarmListAdapter = new AlarmRecyclerViewAdapter(alarms);  // alarms были получены при создании фрагмента
        rvLayoutManage = new LinearLayoutManager(layout.getContext());
        rvAlarmList.setLayoutManager(rvLayoutManage);
        rvAlarmList.setAdapter(rvAlarmListAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_alarm:
                if (fragmentManager == null) fragmentManager = getActivity().getSupportFragmentManager();
                CreateAlarmFragment createAlarmFragment = CreateAlarmFragment.newInstance(dbWorker.getNewId());
                createAlarmFragment.setTargetFragment(AlarmListFragment.this, REQUEST_CODE_CREATE_ALARM);
                createAlarmFragment.show(fragmentManager, TAG_CREATE_ALARM);
                break;
        }
    }

//-------------------------------Обработка созданного будильника----------------------------------//
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CREATE_ALARM) {
            // TODO сообщение о том, через сколько времени прозвенит будильник
            Alarm currentAlarm = (Alarm) data.getSerializableExtra(CreateAlarmFragment.EXTRA_ALARM);
            saveAlarm(currentAlarm);
            long nextNearestAlarmTimeMillis = startAlarm(currentAlarm);
            addNewAlarmInList(currentAlarm);
            toastManager.showNextAlarmInfo(nextNearestAlarmTimeMillis);
        }
    }
    private void saveAlarm(Alarm currentAlarm) {
        dbWorker.putEntity(currentAlarm);
    }

    private long startAlarm(Alarm currentAlarm) {
        long nextAlarmTimeMillis;   // время до запуска
        boolean isNextAlarm = true; long nextNearestAlarmTimeMillis = 0; // данные для информации о ближайшем запуске

        for (int dayOfWeek = 0; dayOfWeek < currentAlarm.getDays().length; dayOfWeek++) {
            if (currentAlarm.getDays()[dayOfWeek]) {    // если выбран день недели
                nextAlarmTimeMillis =
                        getNextAlarmTimeMillis(dayOfWeek, currentAlarm);  // день недели в alarm с 0
                Intent alarmIntent =
                        alarmClockManager.createAlarmIntent(getActivity(), AlarmReceiver.class, currentAlarm);
                Log.d(ALARM_CREATE_TAG, "alarmIntent created");
                PendingIntent alarmPendingIntent =
                        alarmClockManager.createAlarmPendingIntent(getActivity(), currentAlarm.getId(), alarmIntent);
                Log.d(ALARM_CREATE_TAG, "alarmPendingIntent created");
                alarmClockManager
                        .setAlarmOnTime(nextAlarmTimeMillis,
                                AlarmManager.INTERVAL_DAY * getResources().getInteger(R.integer.week_days_number),  // запустить через неделю
                                alarmPendingIntent);
                if (isNextAlarm) {
                    isNextAlarm = false;
                    nextNearestAlarmTimeMillis = nextAlarmTimeMillis;
                }
            }
        }

        return nextNearestAlarmTimeMillis;
    }
    private long getNextAlarmTimeMillis(int dayOfWeek, Alarm currentAlarm) {
        Calendar currentCalendar = new GregorianCalendar();
        Calendar nextAlarmCalendar = (Calendar) currentCalendar.clone();

        // TODO повысить точность настройки. Утилита подсчёта
        int convertedDayOfWeek = (dayOfWeek + 2) % 7;    // в классе Calendar дни начнаются с воскресенья
        int convertedDayOfMonth = (currentCalendar.get(Calendar.DAY_OF_MONTH) +
                getToNextAlarmDaysNumber(
                        currentCalendar.get(Calendar.DAY_OF_WEEK),
                        convertedDayOfWeek)) % currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int convertedDayOfYear = (currentCalendar.get(Calendar.DAY_OF_YEAR) +
                getToNextAlarmDaysNumber(
                    currentCalendar.get(Calendar.DAY_OF_WEEK),
                    convertedDayOfWeek)) % currentCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
        int convertedHourOfDay =
                getToNextAlarmHoursNumber(currentAlarm.getHour(), nextAlarmCalendar);

        nextAlarmCalendar.set(Calendar.SECOND, currentCalendar.get(Calendar.SECOND));
        nextAlarmCalendar.set(Calendar.MINUTE, currentAlarm.getMinute());
        nextAlarmCalendar.set(Calendar.HOUR_OF_DAY, convertedHourOfDay);
        nextAlarmCalendar.set(Calendar.HOUR, currentAlarm.getHour());
        nextAlarmCalendar.set(Calendar.DAY_OF_WEEK, convertedDayOfWeek);    // в календаре дни недели с 1
        nextAlarmCalendar.set(Calendar.DAY_OF_MONTH, convertedDayOfMonth);
        nextAlarmCalendar.set(Calendar.DAY_OF_YEAR, convertedDayOfYear);

        long m1 = nextAlarmCalendar.getTimeInMillis();
        long m2 = currentCalendar.getTimeInMillis();
        long result = Math.abs(m1 - m2);
        return result;
    }
    private int getToNextAlarmDaysNumber(int currentDay, int nextDay) {
        if (currentDay > nextDay)
            return currentDay - getResources().getInteger(R.integer.week_days_number) + nextDay;
        else if (currentDay < nextDay)
            return nextDay - currentDay;
        else    // currentDay = nextDay
            return 0;
    }
    private int getToNextAlarmHoursNumber(int hour, Calendar nextAlarmCalendar) {
        if (hour > 12) {
            hour -= 12;
            nextAlarmCalendar.set(Calendar.AM_PM, Calendar.PM);
        } else {
            nextAlarmCalendar.set(Calendar.AM_PM, Calendar.AM);
        }
        return hour;
    }

    private void addNewAlarmInList(Alarm currentAlarm) {
        alarms.add(currentAlarm);
        rvAlarmListAdapter.updateAlarms();
    }

    public void deleteAllAlarms() {
        // удаелние всех отложенных намерений
        alarmClockManager.deleteAllPendingIntent(alarms);
        // очистка списка будильников(в блоке логики и отображения)
        rvAlarmListAdapter.clear();
        alarms = rvAlarmListAdapter.getAlarms();
        // очистка базы данных
        dbWorker.deleteAll();
        // ToastManager
        toastManager.showInfo(getString(R.string.message_info_delete_all_entries), Toast.LENGTH_SHORT);
    }
}
