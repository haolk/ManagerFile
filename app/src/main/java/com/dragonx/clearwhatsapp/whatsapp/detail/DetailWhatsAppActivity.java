package com.dragonx.clearwhatsapp.whatsapp.detail;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dragonx.clearwhatsapp.FileItem;
import com.dragonx.clearwhatsapp.R;
import com.dragonx.clearwhatsapp.SizeUnit;
import com.dragonx.clearwhatsapp.Store;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoLeK
 * Created on 28/11/2018
 */
@EActivity(R.layout.activity_whats_app_detail)
public class DetailWhatsAppActivity extends AppCompatActivity {

    @ViewById(R.id.tlHeader)
    TabLayout tlHeader;
    @ViewById(R.id.viewPager)
    ViewPager viewPager;
    @ViewById(R.id.tvClearJunk)
    TextView tvClearJunk;

    @Extra
    ArrayList<FileItem> fileItemReceiver;
    @Extra
    ArrayList<FileItem> fileItemSent;
    @Extra
    Boolean isPhoto;
    @Extra
    Boolean isVideo;

    public List<FileItem> fileSelected = new ArrayList<>();
    private Long selectedSize = 0L;
    private Store mStore;
    DetailAdapter detailAdapter;

    @AfterViews
    void afterViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_detail);
        initData();
        initListener();
        setViewPager();
    }

    @OptionsItem(android.R.id.home)
    void clickHome() {
        onBackPressed();
    }

    private void initData() {
        tvClearJunk.setText(getString(R.string.clear_junk_detail, SizeUnit.readableSizeUnit(selectedSize)));
    }

    private void setViewPager() {
        detailAdapter = new DetailAdapter(getSupportFragmentManager());
        detailAdapter.setData(fileItemReceiver, fileItemSent, isPhoto, isVideo);
        viewPager.setAdapter(detailAdapter);
        viewPager.getAdapter().notifyDataSetChanged();
        tlHeader.setupWithViewPager(viewPager);
    }

    private void initListener() {
        mStore = new Store(this);
        tvClearJunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(DetailWhatsAppActivity.this, "", "Deleting...",
                        true);
                dialog.show();
                if (fileSelected != null && fileSelected.size() > 0) {
                    mStore.deleteFiles(fileSelected);
                    fileItemReceiver.removeAll(fileSelected);
                    fileItemSent.removeAll(fileSelected);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        selectedSize = 0L;
                        detailAdapter.setData(fileItemReceiver, fileItemSent, isPhoto, isVideo);
                        detailAdapter.notifyDataSetChanged();
                        viewPager.getAdapter().notifyDataSetChanged();
                        tvClearJunk.setText(getString(R.string.clear_junk_detail, SizeUnit.readableSizeUnit(selectedSize)));
                    }
                }, 1000);
            }
        });
    }

    public void addFile(FileItem selected) {
        this.fileSelected.add(selected);
        selectedSize += selected.getSize();
        tvClearJunk.setText(getString(R.string.clear_junk_detail, SizeUnit.readableSizeUnit(selectedSize)));
    }

    public void removeFile(FileItem selected) {
        this.fileSelected.remove(selected);
        selectedSize -= selected.getSize();
        tvClearJunk.setText(getString(R.string.clear_junk_detail, SizeUnit.readableSizeUnit(selectedSize)));
    }

    @Override
    public void onBackPressed() {
        setResult(10001);
        super.onBackPressed();
    }
}
