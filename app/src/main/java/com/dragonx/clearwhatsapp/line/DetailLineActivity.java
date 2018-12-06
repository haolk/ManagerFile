package com.dragonx.clearwhatsapp.line;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dragonx.clearwhatsapp.FileItem;
import com.dragonx.clearwhatsapp.FilesAdapter;
import com.dragonx.clearwhatsapp.MyRecyclerViewItemClickListener;
import com.dragonx.clearwhatsapp.R;
import com.dragonx.clearwhatsapp.SizeUnit;
import com.dragonx.clearwhatsapp.Store;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HaoLeK
 * Created on 06/12/2018
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_line_detail)
public class DetailLineActivity extends AppCompatActivity implements MyRecyclerViewItemClickListener.OnItemClickListener {
    @ViewById(R.id.tvClearJunk)
    TextView tvClearJunk;
    @ViewById(R.id.rvData)
    RecyclerView rvData;
    @ViewById(R.id.tvNoData)
    TextView tvNoData;

    @Extra
    ArrayList<FileItem> fileItems;
    @Extra
    Boolean isPhoto;
    @Extra
    Boolean isVideo;

    public List<FileItem> fileSelected = new ArrayList<>();
    private Long selectedSize = 0L;
    private Store mStore;
    private FilesAdapter filesAdapter;
    private Boolean isMultiSelect = false;

    @AfterViews
    void afterViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.title_detail);
        isMultiSelect = false;
        int photoSize = getResources().getDimensionPixelSize(R.dimen.item_gallery_photo_size);
        int gridMargin = getResources().getDimensionPixelSize(R.dimen.item_gallery_margin);
        int spanCount = getResources().getDisplayMetrics().widthPixels / (photoSize + 2 * gridMargin);
        filesAdapter = new FilesAdapter(DetailLineActivity.this, isPhoto, isVideo);
        if (fileItems != null && fileItems.size() > 0) {
            filesAdapter = new FilesAdapter(DetailLineActivity.this, isPhoto, isVideo);
            if (isPhoto || isVideo) {
                rvData.setLayoutManager(new GridLayoutManager(DetailLineActivity.this.getApplicationContext(), spanCount, GridLayoutManager.VERTICAL, false));
                rvData.getRecycledViewPool().setMaxRecycledViews(0, spanCount * (getResources().getDisplayMetrics().heightPixels / photoSize) * 2);
            } else {
                rvData.setLayoutManager(new LinearLayoutManager(DetailLineActivity.this));
            }
            filesAdapter.setFiles(fileItems);
            rvData.setAdapter(filesAdapter);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            rvData.setVisibility(View.GONE);
        }
        filesAdapter.setFiles(fileItems);
        rvData.addOnItemTouchListener(new MyRecyclerViewItemClickListener(DetailLineActivity.this, this, rvData));
        tvClearJunk.setText(getString(R.string.clear_junk_detail, SizeUnit.readableSizeUnit(selectedSize)));
    }

    @OptionsItem(android.R.id.home)
    void clickHome() {
        onBackPressed();
    }

    @Click(R.id.tvClearJunk)
    void clickClearJunk() {
        mStore = new Store(this);
        tvClearJunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(DetailLineActivity.this, "", "Deleting...",
                        true);
                dialog.show();
                if (fileSelected != null && fileSelected.size() > 0) {
                    mStore.deleteFiles(fileSelected);
                    fileItems.removeAll(fileSelected);
                    fileSelected.clear();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        selectedSize = 0L;
                        filesAdapter.notifyDataSetChanged();
                        tvClearJunk.setText(getString(R.string.clear_junk_detail, SizeUnit.readableSizeUnit(selectedSize)));
                    }
                }, 1000);
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(1001);
        super.onBackPressed();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (isMultiSelect) {
            multiSelect(position);
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        if (!isMultiSelect) {
            isMultiSelect = true;
            fileSelected.clear();
        }
        multiSelect(position);
    }

    private void multiSelect(int position) {
        FileItem fileItem = fileItems.get(position);
        if (fileSelected.contains(fileItem)) {
            fileSelected.remove(fileItem);
            this.removeFile(fileItem);
        } else {
            fileSelected.add(fileItem);
            this.addFile(fileItem);
        }
        filesAdapter.setSelectedFile(fileSelected);
        isMultiSelect = fileSelected.size() > 0;
    }

    private void addFile(FileItem selected) {
        this.fileSelected.add(selected);
        selectedSize += selected.getSize();
        tvClearJunk.setText(getString(R.string.clear_junk_detail, SizeUnit.readableSizeUnit(selectedSize)));
    }

    private void removeFile(FileItem selected) {
        this.fileSelected.remove(selected);
        selectedSize -= selected.getSize();
        tvClearJunk.setText(getString(R.string.clear_junk_detail, SizeUnit.readableSizeUnit(selectedSize)));
    }
}
