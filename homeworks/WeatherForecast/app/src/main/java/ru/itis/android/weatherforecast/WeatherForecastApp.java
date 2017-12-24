package ru.itis.android.weatherforecast;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.itis.android.weatherforecast.api.WeatherForecastApiInterface;

/**
 * Created by Users on 02.11.2017.
 *
 *  Класс инициализации Retrofit и интерфеса взаимодействия
 *  - Единственный класс Retrofit
 *  - Единственный класс WeatherForecastApiInterface
 *
 *  ! Предварительная инициализации предотвратит множественное создание данных объектов
 *    И даст к ним доступ из любого места приложения(т.к extends Application)
 *
 */

public class WeatherForecastApp extends Application {
    private WeatherForecastApiInterface weatherForecastApi;

    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())                         // клиент
                .baseUrl(WeatherForecastApiInterface.BASE_URL)      // хост
                .addConverterFactory(GsonConverterFactory.create()) // конвертер JSON в POJO
                .build();

        weatherForecastApi =         // объект предоставляющий интерфейс для взаимодействия с хостом
                retrofit.create(WeatherForecastApiInterface.class);
    }

    public WeatherForecastApiInterface getApi() {
        return weatherForecastApi;
    }
}
