package com.dragonx.clearwhatsapp.detail;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class DetailAdapter extends FragmentStatePagerAdapter {

    private List<PagerItem> pagerItems;

    public DetailAdapter(FragmentManager fm, List<PagerItem> pagerItems) {
        super(fm);
        this.pagerItems = pagerItems;
    }

    @Override
    public Fragment getItem(int i) {
        return pagerItems.get(i).getFragment();
    }

    @Override
    public int getCount() {
        return pagerItems.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pagerItems.get(position).getTitle();
    }
}
