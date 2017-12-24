package ru.itis.android.weatherforecast.utils;

import android.app.Activity;
import android.widget.ImageView;

import java.util.Calendar;

import ru.itis.android.weatherforecast.R;
import ru.itis.android.weatherforecast.activities.start.StartActivity;

/**
 * Created by Users on 03.11.2017.
 */

public class DayCycleController {
    public static final String DAY_CYCLE_MORNING = "morning";
    public static final String DAY_CYCLE_AFTERNOON = "afternoon";
    public static final String DAY_CYCLE_EVENING = "evening";
    public static final String DAY_CYCLE_NIGHT = "night";

    private static DayCycleController dayCycleController;

    private int backgroundImageId;
    private int textAppearance;

    public int getBackgroundImageId() {
        return backgroundImageId;
    }

    public int getTextAppearance() {
        return textAppearance;
    }

    private DayCycleController() {
        confirmDayCycle();
    }

    public static DayCycleController getInstance() {
        if (dayCycleController == null) {
            return new DayCycleController();
        }
        return dayCycleController;
    }

    private void confirmDayCycle() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        switch (this.getDayCycle(hour)) {
            case DAY_CYCLE_MORNING:
                backgroundImageId = R.drawable.bg_morning;
                textAppearance = R.style.TextAppearanceMorning;
                break;
            case DAY_CYCLE_AFTERNOON:
                backgroundImageId = R.drawable.bg_afternoon;
                textAppearance = R.style.TextAppearanceAfternoon;
                break;
            case DAY_CYCLE_EVENING:
                backgroundImageId = R.drawable.bg_evening;
                textAppearance = R.style.TextAppearanceEvening;
                break;
            case DAY_CYCLE_NIGHT:
                backgroundImageId = R.drawable.bg_night;
                textAppearance = R.style.TextAppearanceNight;
                break;
        }
    }

    private String getDayCycle(int hour) {
        if (hour >= 6 && hour < 12) {
            return DAY_CYCLE_MORNING;
        } else if (hour >= 12 && hour < 17) {
            return DAY_CYCLE_AFTERNOON;
        } else if (hour >= 17 && hour < 21) {
            return DAY_CYCLE_EVENING;
        } else {
            return DAY_CYCLE_NIGHT;
        }
    }
}
