package ru.itis.android.weatherforecast.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.itis.android.weatherforecast.R;
import ru.itis.android.weatherforecast.activities.start.StartActivity;
import ru.itis.android.weatherforecast.models.WeatherForecastWrapper;
import ru.itis.android.weatherforecast.utils.DayCycleController;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherForecastFragment extends BaseFragment implements ForecastViewInterface {
    private Unbinder unbinder;

    @NonNull @BindView(R.id.iv_weather) ImageView ivWeather;
    @NonNull @BindView(R.id.tv_weather_description) TextView tvWeatherDescription;
    @NonNull @BindView(R.id.tv_location) TextView tvLocation;
    @NonNull @BindView(R.id.tv_forecast_temperature) TextView tvForecastTemperature;
    @NonNull @BindView(R.id.tv_forecast_description) TextView tvForecastDescription;

    private WeatherForecastWrapper weatherForecastWrapper;
    private String errorMessage;

    public void setWeatherForecastWrapper(WeatherForecastWrapper weatherForecastWrapper) {
        this.weatherForecastWrapper = weatherForecastWrapper;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(WeatherForecastWrapper weatherForecastWrapper) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        fragment.setWeatherForecastWrapper(weatherForecastWrapper);

        return fragment;
    }
    public static WeatherForecastFragment newInstance(String errorMessage) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        fragment.setErrorMessage(errorMessage);

        return fragment;
    }

    public void setDayCycleSettings(int textAppearanceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvWeatherDescription.setTextAppearance(textAppearanceId);
            tvLocation.setTextAppearance(textAppearanceId);
            tvForecastTemperature.setTextAppearance(textAppearanceId);
            tvForecastDescription.setTextAppearance(textAppearanceId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater
                .inflate(R.layout.fragment_wheather_forecast, container, false);

        unbinder = ButterKnife.bind(this, layout);

        initViews();

        return layout;
    }

    private void initViews() {
        if (errorMessage == null) {
            ivWeather.setImageResource(weatherForecastWrapper.getIconId());
            tvWeatherDescription.setText(weatherForecastWrapper.getWeatherDescription());
            tvLocation.setText(weatherForecastWrapper.getLocation());
            tvForecastTemperature.setText(String.valueOf(weatherForecastWrapper.getTemperature()) +
                    WeatherForecastWrapper.CELSIUS_MEASURING);
            tvForecastDescription.setText(weatherForecastWrapper.getForecastDescription());
        } else {
            tvForecastDescription.setText(errorMessage);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) unbinder.unbind();
    }
}
