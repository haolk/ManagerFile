package com.dragonx.clearwhatsapp.detail;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dragonx.clearwhatsapp.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoLeK
 * Created on 28/11/2018
 */
@EActivity(R.layout.activity_detail)
public class DetailActivity extends AppCompatActivity {

    @ViewById(R.id.tlHeader)
    TabLayout tlHeader;
    @ViewById(R.id.viewPager)
    ViewPager viewPager;

    @Extra
    ArrayList<FileItem> fileItemReceiver;
    @Extra
    ArrayList<FileItem> fileItemSent;

    private List<PagerItem> fragments = new ArrayList<>();

    @AfterViews
    void afterViews() {
        Fragment receiverFragment = DetailFragment_.builder().fileItems(fileItemReceiver).build();
        fragments.add(new PagerItem("Receiver", receiverFragment));

        Fragment sentFragment = DetailFragment_.builder().fileItems(fileItemSent).build();
        fragments.add(new PagerItem("Sent", sentFragment));

        DetailAdapter detailAdapter = new DetailAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(detailAdapter);
        tlHeader.setupWithViewPager(viewPager);
    }
}
