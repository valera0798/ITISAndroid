package ru.itis.android.events.activities.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.itis.android.events.models.Event;

/**
 * Created by Users on 09.10.2017.
 */

public class EventPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentsList;
    private List<Event> eventList;

    public EventPagerAdapter(FragmentManager fm, List<Event> eventList) {
        super(fm);
        this.eventList = eventList;
        fragmentsList = makeFragments(eventList);
    }

    private List<Fragment> makeFragments(List<Event> eventList) {
        List<Fragment> fragmentList = new ArrayList<>();
        for(Event event: eventList) {
            fragmentList.add(EventDetailFragment.newInstance(event));
        }
        return fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return eventList.get(position).getName();
    }
}
