package ru.itis.android.weatherforecast.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.itis.android.weatherforecast.Presenter;
import ru.itis.android.weatherforecast.R;
import ru.itis.android.weatherforecast.api.WeatherForecastApiInterface;
import ru.itis.android.weatherforecast.models.WeatherForecast;
import ru.itis.android.weatherforecast.models.WeatherForecastWrapper;

/**
 * Created by Users on 19.11.2017.
 */

public class ForecastLoader extends AsyncTaskLoader {
    private WeatherForecastApiInterface apiInterface;
    private Presenter presenter;

    public ForecastLoader(Context context, WeatherForecastApiInterface apiInterface, Presenter presenter) {
        super(context);
        this.apiInterface = apiInterface;
        this.presenter = presenter;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        Call<WeatherForecast> c = apiInterface.getForecastByCityAndCountry("Kazan,ru", WeatherForecastApiInterface.API_ID);
        c.enqueue(presenter);    // запуск запроса с указание обработичка

        // обработка запроса будет происходить в реализации Callback
        return null;
    }
}
