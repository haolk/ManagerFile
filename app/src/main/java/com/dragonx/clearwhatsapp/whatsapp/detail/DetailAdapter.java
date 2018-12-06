package com.dragonx.clearwhatsapp.whatsapp.detail;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.dragonx.clearwhatsapp.FileItem;

import java.util.ArrayList;

public class DetailAdapter extends FragmentPagerAdapter {
    ArrayList<FileItem> fileItemReceiver;
    ArrayList<FileItem> fileItemSent;
    Boolean isPhoto;
    Boolean isVideo;

    public DetailAdapter(FragmentManager fm) {
        super(fm);
    }

    void setData(ArrayList<FileItem> fileItemReceiver,
                 ArrayList<FileItem> fileItemSent,
                 Boolean isPhoto,
                 Boolean isVideo) {
        this.fileItemReceiver = fileItemReceiver;
        this.fileItemSent = fileItemSent;
        this.isPhoto = isPhoto;
        this.isVideo = isVideo;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return DetailFragment_.builder().fileItems(fileItemReceiver).isPhoto(isPhoto).isVideo(isVideo).build();
            case 1:
                return DetailFragment_.builder().fileItems(fileItemSent).isPhoto(isPhoto).isVideo(isVideo).build();
            default:
                return DetailFragment_.builder().fileItems(fileItemReceiver).isPhoto(isPhoto).isVideo(isVideo).build();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Receiver";
            case 1:
                return "Sent";
            default:
                return "Receiver";
        }
    }

    @Override
    public int getItemPosition(Object object) {
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }

}
