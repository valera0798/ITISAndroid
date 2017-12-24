package ru.itis.android.weatherforecast.loader;

import ru.itis.android.weatherforecast.models.WeatherForecastWrapper;

/**
 * Created by Users on 19.11.2017.
 */

public class LoaderResult {
    private WeatherForecastWrapper weatherForecastWrapper;
    private String errorMessage;

    public LoaderResult(WeatherForecastWrapper weatherForecastWrapper, String errorMessage) {
        this.weatherForecastWrapper = weatherForecastWrapper;
        this.errorMessage = errorMessage;
    }

    public WeatherForecastWrapper getWeatherForecast() {
        return weatherForecastWrapper;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
