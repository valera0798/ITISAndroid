package ru.itis.android.weatherforecast.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Field;

/**
 * Created by Users on 04.11.2017.
 *
 * Обёртка над классом WeatherForecast.
 * Для удобства взаимодействия с логикой
 *
 */

public class WeatherForecastWrapper {
    private static final String SEPARATOR_CITY_COUNTRY = ",";
    private static final float KELVIN_CELSIUS = (float) 273.15;

    private static final String PRESSURE = "Pressure: ";        // атмосферное давление
    private static final String HUMIDITY  = "Humidity: ";       // влажность воздуха
    private static final String WIND_SPEED  = "Wind speed: ";   // скорость ветра

    private static final String PRESSURE_MEASURING = " hPa";
    private static final String HUMIDITY_MEASURING = " %";
    private static final String WIND_SPEED_MEASURING = " meter/sec";
    public static final String CELSIUS_MEASURING = " \u00b0C";

    private static final String ICON_PREFIX_WEATHER = "weather_";

    private WeatherForecast weatherForecast;

    private int iconId;
    private String weatherDescription;
    private String location;
    private int temperature;
    private String forecastDescription;

    public WeatherForecastWrapper(WeatherForecast weatherForecast, Context context) {
        this.weatherForecast = weatherForecast;
        iconId = context.getResources().getIdentifier(ICON_PREFIX_WEATHER + weatherForecast.getWeather().get(0).getIcon(),
                "drawable", context.getPackageName());
        weatherDescription = weatherForecast.getWeather().get(0).getDescription();
        location = weatherForecast.getName() + SEPARATOR_CITY_COUNTRY + weatherForecast.getSys().getCountry().toUpperCase();
        temperature = Math.round(weatherForecast.getMain().getTemp() - KELVIN_CELSIUS);
        forecastDescription = getForecastDescription(new StringBuilder());
    }

    public int getIconId() {
        return iconId;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getLocation() {
        return location;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getForecastDescription() {
        return forecastDescription;
    }

    private String getForecastDescription(StringBuilder builder) {
        builder.append(PRESSURE).append(weatherForecast.getMain().getPressure()).append(PRESSURE_MEASURING).append("\r\n")
                .append(HUMIDITY).append(weatherForecast.getMain().getHumidity()).append(HUMIDITY_MEASURING).append("\r\n")
                .append(WIND_SPEED).append(weatherForecast.getWind().getSpeed()).append(WIND_SPEED_MEASURING).append("\r\n");
        return builder.toString();
    }
}
