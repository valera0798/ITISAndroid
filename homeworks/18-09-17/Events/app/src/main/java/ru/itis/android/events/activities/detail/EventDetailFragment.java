package ru.itis.android.events.activities.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.itis.android.events.R;
import ru.itis.android.events.models.Event;

/**
 * Created by Users on 09.10.2017.
 */

public class EventDetailFragment extends Fragment {
    private static final String KEY_PHOTO_ID = "keyPhotoId";
    private static final String KEY_NAME = "keyName";
    private static final String KEY_DESCRIPTION = "keyDescription";
    private static final String KEY_DATE = "keyDate";

    private int photoId;
    private String name;
    private String description;
    private String date;

    public static EventDetailFragment newInstance(Event event) {
        Bundle args = new Bundle();
        args.putInt(KEY_PHOTO_ID, event.getPhotoId());
        args.putString(KEY_NAME, event.getName());
        args.putString(KEY_DESCRIPTION, event.getDescription());
        args.putString(KEY_DATE, event.getDate().toString());

        EventDetailFragment fragment = new EventDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataFromActivity(getArguments());
    }

    private void getDataFromActivity(Bundle arguments) {
        photoId = arguments.getInt(KEY_PHOTO_ID);
        name = arguments.getString(KEY_NAME);
        description = arguments.getString(KEY_DESCRIPTION);
        date = arguments.getString(KEY_DATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_event, container, false);
        initViews(layout);
        return layout;
    }

    private void initViews(View layout) {
        ((ImageView) layout.findViewById(R.id.iv_event_photo)).setImageResource(photoId);
        ((TextView) layout.findViewById(R.id.tv_event_name)).setText(name);
        ((TextView) layout.findViewById(R.id.tv_event_description)).setText(description);
        ((TextView) layout.findViewById(R.id.tv_event_date)).setText(date);
    }
}
