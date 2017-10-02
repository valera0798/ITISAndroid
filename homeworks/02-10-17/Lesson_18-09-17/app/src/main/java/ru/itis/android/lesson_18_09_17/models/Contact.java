package ru.itis.android.lesson_18_09_17.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import java.util.Date;
import java.util.List;

/**
 * Created by Users on 18.09.2017.
 */

public class Contact implements Parcelable {
    private int id;
    private String name;
    @DrawableRes
    private int drawableId;
    private List<PhoneNumber> phoneNumbersList;
    private Date callAtDate;

    public Contact(int id, String name, int drawableId, List<PhoneNumber> phoneNumbersList) {
        this.id = id;
        this.name = name;
        this.drawableId = drawableId;
        this.phoneNumbersList = phoneNumbersList;
    }

    public String getName() {
        return name;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public List<PhoneNumber> getPhoneNumbersList() {
        return phoneNumbersList;
    }

    public Date getCallAtDate() {
        return callAtDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Contact(Parcel in) {
        id = in.readInt();
        name = in.readString();
        drawableId = in.readInt();
        phoneNumbersList = in.createTypedArrayList(PhoneNumber.CREATOR);
        callAtDate = ((Date) in.readSerializable());
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(drawableId);
        parcel.writeTypedList(phoneNumbersList);
        parcel.writeSerializable(callAtDate);
    }

    public void setCallAtDate(Date callAtDate) {
        this.callAtDate = callAtDate;
    }
}
