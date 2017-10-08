package ru.itis.android.lesson_18_09_17.controllers;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import ru.itis.android.lesson_18_09_17.activities.contactpager.ContactFragment;

/**
 * Created by Users on 08.10.2017.
 */

public class OnPhoneNumberClickListener implements View.OnClickListener {
    private Context context;
    private String chooserTitle;
    private String phoneNumber;

    public OnPhoneNumberClickListener(Context context, String chooserTitle) {
        this.context = context;
        this.chooserTitle = chooserTitle;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void onClick(View view) {
        Intent dialIntent = ContactFragment.makeDialIntent(phoneNumber);
        context.startActivity(Intent.createChooser(dialIntent, chooserTitle));
    }
}
