package ru.itis.android.lesson_18_09_17.activities.contactpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.itis.android.lesson_18_09_17.models.Contact;

/**
 * Created by Users on 25.09.2017.
 */

public class ContactPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;

    public ContactPagerAdapter(FragmentManager fm, List<Contact> contacts) {
        super(fm);
        fragments = makeFragments(contacts);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private List<Fragment> makeFragments(List<Contact> contacts) {
        List<Fragment> fragments = new ArrayList<>();
        for (Contact contact: contacts) {
            fragments.add(ContactFragment.newInstance(contact));
        }
        return fragments;
    }
}
