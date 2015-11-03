package com.arunpn.twitterapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.arunpn.twitterapp.R;
import com.arunpn.twitterapp.fragments.MentionsFragment;
import com.arunpn.twitterapp.fragments.TimeLineFragment;

/**
 * Created by a1nagar on 10/31/15.
 */
public class HomeFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {
    Context context;
    String[] tabTitles;
    FragmentManager fm;

    public HomeFragmentPagerAdapter(FragmentManager fm ,Context context) {
        super(fm);
        this.context = context;
        this.tabTitles = context.getResources().getStringArray(R.array.tab_headers);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new TimeLineFragment();
            case 1:
                return new MentionsFragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}
