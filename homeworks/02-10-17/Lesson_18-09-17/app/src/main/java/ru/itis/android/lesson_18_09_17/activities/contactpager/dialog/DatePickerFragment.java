package ru.itis.android.lesson_18_09_17.activities.contactpager.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.itis.android.lesson_18_09_17.R;

/**
 * Created by Users on 01.10.2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePicker.OnDateChangedListener, DialogInterface.OnClickListener {
    public static final String EXTRA_DATE = "extraDate";
    public static final int DEFAULT_YEAR = 2017;
    public static final int DEFAULT_MONTH = 10;
    public static final int DEFAULT_DAY = 1;

    private Date date;  // текущая дата

    public static DatePickerFragment newInstance(Date date) {   // создание DatePicker с определенной датой
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public void sendResult(int resultCode) {    // отправка выбранной даты целевому фрагменту
        if (getTargetFragment() != null) {      // если указан целевой фрагмент
            Intent intent = new Intent();       // создание намерения
            intent.putExtra(EXTRA_DATE, date);  // упаковка выбранной даты

            getTargetFragment()                 // целенаправленный вызов метода активности
                    .onActivityResult(getTargetRequestCode(), resultCode, intent);  // хранящей целевой фрагмент
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        date = (Date) getArguments().getSerializable(EXTRA_DATE);   // получение даты из фрагмента
        View layout = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);   // построение макета выбора даты
        int year, month, day;                           // данные DatePicker
        if (date != null) {                             // если в фрагменте содержалась информация
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } else {                                        // иначе устновить данные по умолчанию
            year = DEFAULT_YEAR;
            month = DEFAULT_MONTH;
            day = DEFAULT_DAY;
        }
        DatePicker datePicker = layout.findViewById(R.id.dp_call_date);
        datePicker.init(year, month, day, this);        // установка данных в макет

        return new AlertDialog.Builder(getActivity())   // создание диалогового окна
                .setView(layout)                        // на основе макета layout
                .setTitle(R.string.date_picker_title)            // заголовок диалогового окна
                .setPositiveButton(android.R.string.ok, this)   // настройка кнопки
                .create();                              // метода создания на основе настроек
    }
    // метод, вызываемый при установке даты в DatePicker
    @Override
    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
        date = new GregorianCalendar(year, month, day).getTime();   // создание объекта Date на основке параметров
        getArguments().putSerializable(EXTRA_DATE, date);           // добавление даты в аргументы фрагмента
    }
    // метод, вызываемый при подтверждении выбора даты
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        sendResult(Activity.RESULT_OK); // отправка результата выбора даты в целевой фрагмент
    }
}
