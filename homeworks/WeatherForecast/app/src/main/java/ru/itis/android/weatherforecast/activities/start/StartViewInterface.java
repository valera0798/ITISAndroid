package ru.itis.android.weatherforecast.activities.start;

import ru.itis.android.weatherforecast.fragments.BaseFragment;
import ru.itis.android.weatherforecast.loader.LoaderResult;

/**
 * Created by Users on 18.11.2017.
 */

public interface StartViewInterface {
    void showHttpRequestResult(LoaderResult loaderResult);
    void updateDate(int textAppearance);
    void confirmDayCycle(int backgroundId);
}
