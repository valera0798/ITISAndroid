package ru.itis.android.imageapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Users on 19.11.2017.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private List<Image> images;

    public ImageListAdapter(List<Image> images) {
        this.images = images;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_image, parent, false);
        return new ImageViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        holder.bind(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }
}
