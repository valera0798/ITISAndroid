package ru.itis.android.weatherforecast.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.itis.android.weatherforecast.models.WeatherForecast;

/**
 * Created by Users on 02.11.2017.
 *
 * Интерфейс определяющий конечную точку перед доступом к ресурсу
 * - Опеределены сценарии доступов
 * - Определена специфика доступа к ресурсу
 *
 */

public interface WeatherForecastApiInterface {
    String API_ID = "9413877186d51d07d87da826c9b77d37"; // ключ для получения для авторизации на ресурсу

    String BASE_URL = "http://api.openweathermap.org/";    // URL
    String BASE_URL_PATH = "data/2.5/weather";

    @GET(BASE_URL_PATH)
    Call<WeatherForecast> getForecastByCityAndCountry(@Query("q") String cityNameAndCountryCode, @Query("appid") String appId);

    @GET(BASE_URL_PATH)
    Call<WeatherForecast> getForecastById(@Query("id") long placeId);
}
