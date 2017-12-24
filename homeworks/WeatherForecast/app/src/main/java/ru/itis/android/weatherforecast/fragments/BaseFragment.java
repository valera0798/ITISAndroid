package ru.itis.android.weatherforecast.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.itis.android.weatherforecast.R;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements ForecastViewInterface {
    @Override
    public abstract void setDayCycleSettings(int textAppearanceId);
}
