package ru.itis.android.imageapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements MaterialDialog.SingleButtonCallback {
    @BindView(R.id.rv_list_image)
    RecyclerView rvImages;

    private Unbinder unbinder;
    private ImageListAdapter adapter;
    private LinearLayoutManager layoutManager;
    private MaterialDialog startDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        startDialog = new MaterialDialog.Builder(this)
                .title(R.string.title)
                .content(R.string.content)
                .
                .autoDismiss(false)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)
                .onPositive(this)
                .onNegative(this)
                .build();
        startDialog.setCancelable(false);
        startDialog.setCanceledOnTouchOutside(false);
        startDialog.show();
    }

    public List<Image> getImages(String[] urls, String[] names) {
        List<Image> images = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            images.add(new Image(urls[i], names[i]));
        }
        return images;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        switch (which.name()) {
            case "POSITIVE":
                download();
                break;
            case "NEGATIVE":
                cancel();
                break;
        }
    }

    private void download() {
        startDialog.cancel();
        layoutManager = new LinearLayoutManager(this);
        rvImages.setLayoutManager(layoutManager);
        adapter = new ImageListAdapter(
                getImages(getResources().getStringArray(R.array.url_images),
                        getResources().getStringArray(R.array.name_images))
        );
        rvImages.setAdapter(adapter);
    }
    private void cancel() {
        this.finish();
    }
}
