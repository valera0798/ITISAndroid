package ru.itis.android.weatherforecast;

import android.app.Activity;
import android.app.Application;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.test.mock.MockApplication;
import android.util.Log;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.itis.android.weatherforecast.activities.start.StartActivity;
import ru.itis.android.weatherforecast.activities.start.StartViewInterface;
import ru.itis.android.weatherforecast.api.WeatherForecastApiInterface;
import ru.itis.android.weatherforecast.fragments.BaseFragment;
import ru.itis.android.weatherforecast.fragments.ForecastViewInterface;
import ru.itis.android.weatherforecast.fragments.WeatherForecastFragment;
import ru.itis.android.weatherforecast.loader.ForecastLoader;
import ru.itis.android.weatherforecast.loader.LoaderResult;
import ru.itis.android.weatherforecast.models.WeatherForecast;
import ru.itis.android.weatherforecast.models.WeatherForecastWrapper;
import ru.itis.android.weatherforecast.utils.DayCycleController;
import ru.itis.android.weatherforecast.utils.DayCycleSettings;

/**
 * Created by Users on 18.11.2017.
 */

public class Presenter implements LoaderManager.LoaderCallbacks<LoaderResult>, Callback<WeatherForecast> {
    public static final String LOG_D_TAG = "Retrofit debug.";
    private static final int LOADER_GET_FORECAST = 1;

    private static Presenter presenter;

    private Application application;
    private Activity activity;
    private StartViewInterface startViewInterface;
    private DayCycleController dayCycleController;
    private LoaderManager loaderManager;

    private Presenter(Application application, Activity activity, StartViewInterface startViewInterface, LoaderManager loaderManager) {
        this.application = application;
        this.activity = activity;
        this.startViewInterface = startViewInterface;
        this.loaderManager = loaderManager;

        dayCycleController = DayCycleController.getInstance();
    }

    public static Presenter getInstance(Application application, Activity activity, StartViewInterface startViewInterface, LoaderManager loaderManager) {
        if (presenter == null)
            presenter = new Presenter(application, activity, startViewInterface, loaderManager);
        return presenter;
    }

    public DayCycleSettings getDataCycleSettings() {
        return new DayCycleSettings(
                dayCycleController.getBackgroundImageId(),
                dayCycleController.getTextAppearance());
    }

    public void getForecast() {
        loaderManager.initLoader(LOADER_GET_FORECAST,   // Loader id
                null,                                   // Loader bundle
                this);                                  // Loader lifecycle handler

        startViewInterface.updateDate(dayCycleController.getTextAppearance());
    }
    public void viewForecast(LoaderResult loaderResult) {
        startViewInterface.showHttpRequestResult(loaderResult);     // отобразить полученные данные
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case LOADER_GET_FORECAST:
                return new ForecastLoader(
                        activity.getApplicationContext(),
                        ((WeatherForecastApp) application).getApi(),
                        this);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, LoaderResult loaderResult) {
        if (loaderResult != null) {
            switch (loader.getId()) {
                case LOADER_GET_FORECAST:
                    viewForecast(loaderResult);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
        Log.d(LOG_D_TAG, "Response: " + response.toString());
        Log.d(LOG_D_TAG, "Response code: " + String.valueOf(response.code()));
        Log.d(LOG_D_TAG, "Response message: " + response.message());
        Log.d(LOG_D_TAG, "Response isSuccessful: " + response.isSuccessful());
        Log.d(LOG_D_TAG, "Response errorBody: " + response.errorBody());
        Set<String> headers = response.headers().names();
        for (String header : headers) {
            Log.d(LOG_D_TAG, "Response header: " + header);
        }

        if (response.isSuccessful()) {
            WeatherForecast weatherForecast = response.body();
            onLoadFinished(
                    loaderManager.getLoader(LOADER_GET_FORECAST),
                    new LoaderResult(new WeatherForecastWrapper(weatherForecast, activity), null));
        } else {
            onLoadFinished(
                    loaderManager.getLoader(LOADER_GET_FORECAST),
                    new LoaderResult(null, activity.getString(R.string.error_response)));
        }
    }

    @Override
    public void onFailure(Call<WeatherForecast> call, Throwable t) {
    }
}
