package ru.itis.android.imageapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Users on 19.11.2017.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_img)
    ImageView ivImage;
    @BindView(R.id.item_tv)
    TextView tvName;

    private Unbinder unbinder;

    private Context context;

    public ImageViewHolder(View itemView) {
        super(itemView);

        unbinder = ButterKnife.bind(this, itemView);

        context = itemView.getContext();
    }

    public void bind(Image image) {
        Glide.with(context)
                .load(image.getImageUrl())
                .error(R.drawable.error)
                .crossFade(500)
                .into(ivImage);
        tvName.setText(image.getName());
    }
}
