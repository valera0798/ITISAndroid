package ru.itis.android.events.activities.toplevel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.itis.android.events.R;
import ru.itis.android.events.models.Event;

/**
 * Created by Users on 24.09.2017.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivPhoto;
    private TextView tvName;
    private TextView tvDescription;
    private TextView tvDate;

    public EventViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
        super(itemView);

        initViews(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null)
                    onItemClickListener.onClick(getAdapterPosition());
            }
        });
    }

    private void initViews(View itemView) {
        ivPhoto = itemView.findViewById(R.id.iv_event_photo);
        tvName = itemView.findViewById(R.id.tv_event_name);
        tvDescription = itemView.findViewById(R.id.tv_event_description);
        tvDate = itemView.findViewById(R.id.tv_event_date);
    }

    public void bind(Event event) {
        ivPhoto.setImageResource(event.getPhotoId());
        tvName.setText(event.getName());
        tvDescription.setText(event.getDescription());
        tvDate.setText(event.getDate().toString());
    }
}
