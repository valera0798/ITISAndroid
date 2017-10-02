package ru.itis.android.lesson_18_09_17.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Users on 18.09.2017.
 */

public class PhoneNumber implements Parcelable{
    private String number;
    private String description;

    public PhoneNumber(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected PhoneNumber(Parcel in) {
        number = in.readString();
        description = in.readString();
    }

    public static final Creator<PhoneNumber> CREATOR = new Creator<PhoneNumber>() {
        @Override
        public PhoneNumber createFromParcel(Parcel in) {
            return new PhoneNumber(in);
        }

        @Override
        public PhoneNumber[] newArray(int size) {
            return new PhoneNumber[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(number);
        parcel.writeString(description);
    }
}
