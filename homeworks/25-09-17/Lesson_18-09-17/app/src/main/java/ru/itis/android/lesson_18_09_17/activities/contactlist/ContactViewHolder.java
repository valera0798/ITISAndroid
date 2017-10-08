package ru.itis.android.lesson_18_09_17.activities.contactlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.itis.android.lesson_18_09_17.R;
import ru.itis.android.lesson_18_09_17.models.Contact;

/**
 * Created by Users on 18.09.2017.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivPhoto;
    private TextView tvName;

    public ContactViewHolder(View itemView, final OnItemClickListener onItemClickListener) {
        super(itemView);

        ivPhoto = itemView.findViewById(R.id.iv_photo);
        tvName = itemView.findViewById(R.id.tv_name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            }
        });
    }

    public void bind(Contact contact) {
        tvName.setText(contact.getName());
        ivPhoto.setImageResource(contact.getDrawableId());
    }
}
