package ru.itis.android.test.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Users on 10.09.2017.
 */
// TODO сделать посылаемый тест
public class ParcelableTest extends Test implements Parcelable {
    public ParcelableTest(String[] questions, String[] answers, String separator, int rightAnswerIndex) {
        super(questions, answers, separator, rightAnswerIndex);
    }

    public ParcelableTest(Parcel in) {
        questionList = in.readArrayList(ArrayList.class.getClassLoader());
        currentQuestionIndex = in.readInt();
        rightAnsweredQuestionsNumber = in.readInt();

    }

    public static final Creator<ParcelableTest> CREATOR = new Creator<ParcelableTest>() {
        @Override
        public ParcelableTest createFromParcel(Parcel in) {
            return new ParcelableTest(in);
        }

        @Override
        public ParcelableTest[] newArray(int size) {
            return new ParcelableTest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(questionList);
        parcel.writeInt(currentQuestionIndex);
        parcel.writeInt(rightAnsweredQuestionsNumber);
    }
}
