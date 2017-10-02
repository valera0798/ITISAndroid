package ru.itis.android.lesson_18_09_17.activities.contactpager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import ru.itis.android.lesson_18_09_17.R;
import ru.itis.android.lesson_18_09_17.activities.contactpager.dialog.DatePickerFragment;
import ru.itis.android.lesson_18_09_17.models.Contact;
import ru.itis.android.lesson_18_09_17.models.PhoneNumber;

public class ContactFragment extends Fragment implements View.OnClickListener{
    public static final String KEY_CONTACT = "contacts";
    private static final String TAG_DATE_PICKER = "tagDatePicker";
    private static final int REQUEST_CODE_DATE = 0;

    private Contact contact;

    private ImageView ivContactPhoto;
    private TextView tvContactName;
    private LinearLayout llPhoneNumbers;
    private Button btnSelectDate;

    public static ContactFragment newInstance(Contact contact) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_CONTACT, contact);

        ContactFragment fragment = new ContactFragment();
        fragment.setContact(contact);

        fragment.setArguments(args);

        return fragment;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_contact, container, false);
        initViews(layout);
        initData(contact, inflater);
        return layout;
    }

    private void initViews(View layout) {
        ivContactPhoto = layout.findViewById(R.id.iv_contact_photo);
        tvContactName = layout.findViewById(R.id.tv_contact_name);
        llPhoneNumbers = layout.findViewById(R.id.ll_phone_numbers);
        btnSelectDate = layout.findViewById(R.id.btn_select_date);
        btnSelectDate.setOnClickListener(this);
    }

    private void initData(Contact contact, LayoutInflater inflater) {
        ivContactPhoto.setImageResource(contact.getDrawableId());
        tvContactName.setText(contact.getName());
        updateDate();

        List<PhoneNumber> phoneNumberList = contact.getPhoneNumbersList();
        View layoutPhoneNumber;
        for (PhoneNumber phoneNumber: phoneNumberList) {
            layoutPhoneNumber = inflater.inflate(R.layout.item_phone_number, llPhoneNumbers, false);
            initPhoneNumber(layoutPhoneNumber, phoneNumber);

            llPhoneNumbers.addView(layoutPhoneNumber);
        }
    }

    private void updateDate() {
        if (contact.getCallAtDate() != null) {
            btnSelectDate.setText(contact.getCallAtDate().toString());
        } else {
            btnSelectDate.setText(R.string.btn_select_date);
        }
    }

    private void initPhoneNumber(View layoutPhoneNumber, PhoneNumber phoneNumber) {
        ((TextView) layoutPhoneNumber.findViewById(R.id.tv_info_phone_number))
                .setText(phoneNumber.getNumber());
        ((TextView) layoutPhoneNumber.findViewById(R.id.tv_info_phone_number_description))
                .setText(phoneNumber.getDescription());
    }

    // метод, вызываемый целевым дял получения данных из дочернего фрагмента
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            contact.setCallAtDate(date);
            updateDate();
        }
    }

    @Override
    public void onClick(View view) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(contact.getCallAtDate());    // создание фрагмента выбора даты
        dialog.setTargetFragment(ContactFragment.this, REQUEST_CODE_DATE);  // установка для фрагмента выбора целевого
        dialog.show(manager, TAG_DATE_PICKER);                              // отображение диалогового фрагмента
    }
}
