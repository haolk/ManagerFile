package com.dragonx.clearwhatsapp.detail;

import android.support.v4.app.Fragment;

/**
 * @author HaoLeK
 * Created on 28/11/2018
 */
public class PagerItem {
    private String title;
    private Fragment fragment;

    public PagerItem(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
