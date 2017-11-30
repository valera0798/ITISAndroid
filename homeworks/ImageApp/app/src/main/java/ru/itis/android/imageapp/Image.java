package ru.itis.android.imageapp;

/**
 * Created by Users on 19.11.2017.
 */

public class Image {
    private String imageUrl;
    private String name;

    public Image(String imageUrl, String name) {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }
}
