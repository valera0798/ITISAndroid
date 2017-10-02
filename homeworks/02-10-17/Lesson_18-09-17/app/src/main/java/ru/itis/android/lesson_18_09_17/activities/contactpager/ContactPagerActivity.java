package ru.itis.android.lesson_18_09_17.activities.contactpager;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import ru.itis.android.lesson_18_09_17.R;
import ru.itis.android.lesson_18_09_17.activities.contactlist.ContactsListActivity;
import ru.itis.android.lesson_18_09_17.customview.pagerindicator.PagerIndicator;
import ru.itis.android.lesson_18_09_17.models.Contact;
import ru.itis.android.lesson_18_09_17.models.PhoneNumber;

public class ContactPagerActivity extends AppCompatActivity {
    private static final String KEY_POSITION = "position";
    private static final String KEY_CONTACTS = "contacts";

    private ViewPager vpContacts;
    private ContactPagerAdapter adapter;
    private ArrayList<Contact> contacts;
    //private PagerIndicator pagerIndicator;
    private CircleIndicator circleIndicator;

    public static Intent makeIntent(Context from, int position, List<Contact> contacts) {
        Intent intent = new Intent(from, ContactPagerActivity.class);
        intent.putExtra(KEY_POSITION, position);
        intent.putParcelableArrayListExtra(KEY_CONTACTS, (ArrayList<? extends Parcelable>) contacts);

        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_pager);

        contacts = getIntent().getParcelableArrayListExtra(KEY_CONTACTS);
        int position = getIntent().getIntExtra(KEY_POSITION, 0);    // позиция выбранного элемента списка

        adapter = new ContactPagerAdapter(getSupportFragmentManager(), contacts);
//        pagerIndicator = (PagerIndicator) findViewById(R.id.page_indicator);
//        pagerIndicator.setItemsCount(contacts.size());
        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.ci_indicator);

        vpContacts = (ViewPager) findViewById(R.id.vp_contacts);
        vpContacts.setAdapter(adapter);
        vpContacts.setCurrentItem(position);
        circleIndicator.setViewPager(vpContacts);
        vpContacts.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //circleIndicator.setCurrentPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
