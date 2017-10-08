package ru.itis.android.lesson_18_09_17.customview.pagerindicator;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ru.itis.android.lesson_18_09_17.R;

/**
 * Created by Users on 25.09.2017.
 */

public class PagerIndicator extends LinearLayout {
    private int currentPosition;

    public PagerIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public void setItemsCount(int count) {
        removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView ivIndicatorPassive = new ImageView(getContext());
            ivIndicatorPassive.setImageResource(R.drawable.indicator_passive);
            ivIndicatorPassive.setPadding(5, 0, 5, 0);
            addView(ivIndicatorPassive);
        }

        currentPosition = 0;
        setCurrentPosition(currentPosition);
    }

    public void setCurrentPosition(int position) {
        if (position >= 0 && position < getChildCount()) {
            ((ImageView) getChildAt(currentPosition)).setImageResource(R.drawable.indicator_passive);
            currentPosition = position;
            ((ImageView) getChildAt(currentPosition)).setImageResource(R.drawable.indicator_active);
        }
    }
}
