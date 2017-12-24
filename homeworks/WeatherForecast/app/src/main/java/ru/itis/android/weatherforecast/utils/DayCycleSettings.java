package ru.itis.android.weatherforecast.utils;

/**
 * Created by Users on 19.11.2017.
 */

public class DayCycleSettings {
    private int backgroundImageId;
    private int textAppearance;

    public DayCycleSettings(int backgroundImageId, int textAppearance) {
        this.backgroundImageId = backgroundImageId;
        this.textAppearance = textAppearance;
    }

    public int getBackgroundImageId() {
        return backgroundImageId;
    }

    public int getTextAppearance() {
        return textAppearance;
    }
}
