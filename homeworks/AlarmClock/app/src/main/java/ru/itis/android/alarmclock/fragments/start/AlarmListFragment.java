package ru.itis.android.alarmclock.fragments.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.itis.android.alarmclock.R;
import ru.itis.android.alarmclock.fragments.start.views.AlarmRecyclerViewAdapter;
import ru.itis.android.alarmclock.models.database.DBWorker;
import ru.itis.android.alarmclock.models.database.sqlite.AlarmWrapper;
import ru.itis.android.alarmclock.models.database.sqlite.loaders.AlarmCreateLoader;
import ru.itis.android.alarmclock.models.database.sqlite.loaders.AlarmDeleteAllLoader;
import ru.itis.android.alarmclock.models.database.sqlite.loaders.AlarmSelectAllLoader;
import ru.itis.android.alarmclock.models.database.sqlite.loaders.AlarmSelectByIdLoader;
import ru.itis.android.alarmclock.models.database.sqlite.loaders.AlarmUpdateLoader;
import ru.itis.android.alarmclock.receiver.AlarmReceiver;
import ru.itis.android.alarmclock.models.Alarm;
import ru.itis.android.alarmclock.utils.AlarmClockManager;
import ru.itis.android.alarmclock.utils.ToastManager;

public class AlarmListFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_D_ALARM_CREATE_TAG = "ALARM_CREATE_TAG";

    public static final int LOADER_ID_CREATE_ALARM = 111;
    public static final int LOADER_ID_SELECT_ALL_ALARMS = 222;
    public static final int LOADER_ID_SELECT_BY_ID = 333;
    public static final int LOADER_ID_UPDATE_ALARM = 444;
    public static final int LOADER_ID_DELETE_ALL_ALARMS = 555;

    private static final int REQUEST_CODE_CREATE_ALARM = 1;
    private static final String TAG_CREATE_ALARM = "TAG_CREATE_ALARM";

    private List<Alarm> alarms;

    private AlarmClockManager alarmClockManager;
    private FragmentManager fragmentManager;
    private ToastManager toastManager;
    private LoaderManager loaderManager;
    private DBWorker dbWorker;

    private RecyclerView rvAlarmList;
    private AlarmRecyclerViewAdapter rvAlarmListAdapter;
    private RecyclerView.LayoutManager rvLayoutManage;
    private FloatingActionButton btnCreateAlarm;

    public static AlarmListFragment newInstance(final DBWorker<Alarm> dbWorker, Context appContext) {
        final AlarmListFragment fragment = new AlarmListFragment();
        fragment.setDBWorker(dbWorker);
        fragment.setAlarmClockManager(AlarmClockManager.getInstance(appContext));
        fragment.setToastManager(ToastManager.getInstance(appContext));

        return fragment;
    }
    private void setDBWorker(DBWorker dbWorker) {
        this.dbWorker = dbWorker;
    }
    private void setAlarms() {
        readAllAlarmsAsync();
    }
    private void setAlarmClockManager(AlarmClockManager alarmClockManager) {
        this.alarmClockManager = alarmClockManager;
    }
    private void setToastManager(ToastManager toastManager) {
        this.toastManager = toastManager;
    }

    public AlarmListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loaderManager = getLoaderManager();
        setAlarms();

        View layout = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        initViews(layout);
        return layout;
    }
    private void initViews(View layout) {
        btnCreateAlarm = layout.findViewById(R.id.btn_create_alarm);
        btnCreateAlarm.setOnClickListener(this);
        rvAlarmList = layout.findViewById(R.id.rv_alarm_list);

        rvLayoutManage = new LinearLayoutManager(layout.getContext());
        rvAlarmList.setLayoutManager(rvLayoutManage);
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
//-------------------------------Асинхронная работа с данными-------------------------------------//
    public void createAlarmAsync(Alarm alarm) {
        Bundle args = new Bundle();
        args.putParcelable(AlarmCreateLoader.ALARM_CREATE, alarm);

        loaderManager.restartLoader(LOADER_ID_SELECT_ALL_ALARMS, args, this);
    }

    public void readAllAlarmsAsync() {
        loaderManager.initLoader(LOADER_ID_SELECT_ALL_ALARMS, null, this);
    }

    public void readAlarmByIdAsync(int id, LoaderManager.LoaderCallbacks<Cursor> cursorLoaderCallbacks) {
        Bundle args = new Bundle();
        args.putInt(AlarmSelectByIdLoader.ALARM_SELECT_BY_ID, id);

        loaderManager.initLoader(LOADER_ID_SELECT_ALL_ALARMS, args, cursorLoaderCallbacks);
    }

    public void updateAlarm(Alarm currentAlarm, Alarm updatedAlarm) {
        Bundle args = new Bundle();
        args.putParcelable(AlarmUpdateLoader.ALARM_UPDATE_OLD, currentAlarm);
        args.putParcelable(AlarmUpdateLoader.ALARM_UPDATE_NEW, updatedAlarm);

        loaderManager.restartLoader(LOADER_ID_UPDATE_ALARM, args, this);
    }

    public void deleteAllAlarmsAsync() {
        Bundle args = new Bundle();
        args.putParcelableArrayList(AlarmDeleteAllLoader.ALARM_DELETE_ALL, ((ArrayList<? extends Parcelable>) alarms));

        loaderManager.restartLoader(LOADER_ID_SELECT_ALL_ALARMS, args, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID_CREATE_ALARM:
                return new AlarmCreateLoader(getActivity(), dbWorker, args);
            case LOADER_ID_SELECT_ALL_ALARMS:
                return new AlarmSelectAllLoader(getActivity(), dbWorker);
            case LOADER_ID_UPDATE_ALARM:
                return new AlarmUpdateLoader(getActivity(), dbWorker, args);
            case LOADER_ID_DELETE_ALL_ALARMS:
                return new AlarmDeleteAllLoader(getActivity(), dbWorker, args);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID_CREATE_ALARM:
                loaderManager.destroyLoader(LOADER_ID_CREATE_ALARM);
                break;
            case LOADER_ID_SELECT_ALL_ALARMS:
                initAlarms(new AlarmWrapper(cursor).getAlarms());
                break;
            case LOADER_ID_UPDATE_ALARM:
                loaderManager.destroyLoader(LOADER_ID_UPDATE_ALARM);
                break;
            case LOADER_ID_DELETE_ALL_ALARMS:
                loaderManager.destroyLoader(LOADER_ID_DELETE_ALL_ALARMS);
                break;
        }
    }

    private void initAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        rvAlarmListAdapter = new AlarmRecyclerViewAdapter(alarms, dbWorker, this);  // alarms были получены при создании фрагмента
        rvAlarmList.setAdapter(rvAlarmListAdapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }
//-------------------------------Обработка созданного будильника----------------------------------//
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CREATE_ALARM) {
            Alarm currentAlarm = data.getParcelableExtra(CreateAlarmFragment.EXTRA_ALARM);
            saveAlarm(currentAlarm);
            addNewAlarmInList(currentAlarm);
            long nextNearestAlarmTimeMillis = startAlarm(currentAlarm, AlarmReceiver.class);
            showNextAlarmInto(nextNearestAlarmTimeMillis);
        }
    }

    private void saveAlarm(final Alarm currentAlarm) {
        createAlarmAsync(currentAlarm);
    }

    private void addNewAlarmInList(Alarm currentAlarm) {
        alarms.add(currentAlarm);
        rvAlarmListAdapter.updateAlarms();
    }

    private long startAlarm(Alarm currentAlarm, Class alarmReceiverClass) {
        return alarmClockManager.startAlarm(currentAlarm, alarmReceiverClass);
    }

    private void showNextAlarmInto(long nextNearestAlarmTimeMillis) {
        toastManager.showNextAlarmInfo(nextNearestAlarmTimeMillis);
    }

    public void deleteAllAlarms() {
        // удаелние всех отложенных намерений
        alarmClockManager.deleteAlarms(alarms);
        // очистка списка будильников(в блоке отображения и логики)
        rvAlarmListAdapter.clear();
        alarms = rvAlarmListAdapter.getAlarms();
        // очистка базы данных
        deleteAllAlarmsAsync();

        // ToastManager
        toastManager.showInfo(getString(R.string.message_info_delete_all_entries), Toast.LENGTH_SHORT);
    }
}
