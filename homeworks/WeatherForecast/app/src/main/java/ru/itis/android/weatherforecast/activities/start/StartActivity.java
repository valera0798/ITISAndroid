package ru.itis.android.weatherforecast.activities.start;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import ru.itis.android.weatherforecast.Presenter;
import ru.itis.android.weatherforecast.R;
import ru.itis.android.weatherforecast.WeatherForecastApp;
import ru.itis.android.weatherforecast.api.WeatherForecastApiInterface;
import ru.itis.android.weatherforecast.fragments.BaseFragment;
import ru.itis.android.weatherforecast.fragments.ForecastViewInterface;
import ru.itis.android.weatherforecast.fragments.WeatherForecastFragment;
import ru.itis.android.weatherforecast.loader.LoaderResult;
import ru.itis.android.weatherforecast.models.WeatherForecast;
import ru.itis.android.weatherforecast.utils.DayCycleController;
// TODO MVP, Loaders
public class StartActivity extends AppCompatActivity implements StartViewInterface {
    private Unbinder unbinder;
    private Presenter presenter;

    @NonNull
    @BindView(R.id.iv_activity_start_background)
    ImageView ivBackground;
    @NonNull
    @BindView(R.id.fragment_container_activity_start)
    FrameLayout flFragmentContainer;
    @NonNull
    @BindView(R.id.tv_time_last_update)
    TextView tvLastUpdateTime;
    @NonNull
    @BindView(R.id.btn_update)
    FloatingActionButton btnUpdate;

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        unbinder = ButterKnife.bind(this);

        presenter = Presenter.getInstance(getApplication(), this, this, getLoaderManager());
        confirmDayCycle(presenter.getDataCycleSettings().getBackgroundImageId());
        // автоматический запрос при запуске приложения
        btnUpdate.performClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    public void showHttpRequestResult(LoaderResult loaderResult) {
        BaseFragment baseFragment;
        if (loaderResult.getErrorMessage() != null) {
            baseFragment = WeatherForecastFragment.newInstance(loaderResult.getErrorMessage());
            baseFragment.setDayCycleSettings(presenter.getDataCycleSettings().getTextAppearance());
        } else {
            baseFragment = WeatherForecastFragment.newInstance(loaderResult.getWeatherForecast());
            baseFragment.setDayCycleSettings(presenter.getDataCycleSettings().getTextAppearance());
        }
        if (getSupportFragmentManager().findFragmentById(getContainerId()) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(getContainerId(), baseFragment)
                    .commitAllowingStateLoss();
        }
    }

    public void updateDate(int textAppearance) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tvLastUpdateTime.setTextAppearance(textAppearance);
        }
        tvLastUpdateTime.setText(getString(R.string.info_last_update_time) + " " + simpleDateFormat.format(currentDate));
    }

    public void confirmDayCycle(int backgroundId) {
        ivBackground.setImageResource(backgroundId);
    }

    public int getContainerId() {
        return R.id.fragment_container_activity_start;
    }

    @OnClick(R.id.btn_update)
    public void onClick() {
        presenter.getForecast();
    }
}
