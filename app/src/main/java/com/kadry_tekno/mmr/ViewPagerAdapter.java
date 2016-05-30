package com.kadry_tekno.mmr;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Kadry on 11/05/2016.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            FragmentTab1 tab1 = new FragmentTab1();
            return tab1;
        }
        if(position == 1)             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            TabFragment tab2 = new TabFragment();
            return tab2;
        }
        else              // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            TabFragment tab3 = new TabFragment();
            return tab3;
        }

    }

    @Override
    public int getCount() {
        return 3;           // As there are only 3 Tabs
    }

}
